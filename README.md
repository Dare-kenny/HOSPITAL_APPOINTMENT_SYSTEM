# Hospital Appointment System

A REST API for booking hospital appointments, built with Spring Boot, Spring Security (JWT) and MySQL.

Patients can register, browse open time slots and book an appointment. Doctors can view their schedule of booked appointments. Heads of Department (HODs) register doctors and define the weekly slots each doctor is available for. When a patient books a slot, a confirmation email is sent to them.

## Features

- **Role based access** with three roles: `PATIENT`, `DOCTOR` and `HOD`
- **JWT authentication**: log in once, then send the token on every protected request (tokens expire after 2 hours)
- **Password hashing** with BCrypt, so raw passwords are never stored
- **Slot management**: doctors are registered with weekly slots, each exactly 2 hours long (`HH:mm - HH:mm`)
- **Booking**: a slot can only be booked once; booked slots disappear from the available list
- **Email confirmation** sent to the patient over SMTP after a successful booking

## Tech stack

| Area           | Technology                              |
| -------------- | --------------------------------------- |
| Language       | Java 24                                 |
| Framework      | Spring Boot 3.5.4 (Web, Data JPA, Mail) |
| Security       | Spring Security, JJWT 0.11.5            |
| Database       | MySQL (via `mysql-connector-j`)         |
| Utilities      | Lombok, Jakarta Validation              |
| Build tool     | Maven                                   |

## Project structure

```
HospitalAppointmentSystem/
├── pom.xml
└── src/main/java/com/example/HospitalAppointmentSystem/
    ├── controllers/        # REST endpoints (Auth, Patient, Doctor, HOD)
    ├── services/           # Business logic, authentication, email
    │   └── entityServices/ # Patient, Doctor and HOD specific services
    ├── repositories/       # Spring Data JPA repositories
    ├── models/
    │   ├── entitySchemas/  # Patient, Doctor, HOD (share a UserEntity base)
    │   ├── otherSchemas/   # AllSlots, Appointment
    │   └── enums/          # Role, BookingStatus, DayOfTheWeek
    ├── dtos/               # Request and response objects
    └── configurations/     # Security config and JWT filter/utility
```

## Getting started

### Prerequisites

- JDK 24
- Maven 3.9+
- A running MySQL server
- An email account that can send over SMTP (for Gmail, create an [App Password](https://support.google.com/accounts/answer/185833))

### 1. Clone the repository

```bash
git clone https://github.com/dare-kenny/hospital_appointment_system.git
cd hospital_appointment_system/HospitalAppointmentSystem
```

### 2. Create the database

```sql
CREATE DATABASE hospital_database;
```

Tables are created automatically on startup (`spring.jpa.hibernate.ddl-auto=update`).

### 3. Configure the application

Secrets are read from environment variables, never from committed files. Copy the example file and fill in your own values:

```bash
cp .env.example .env
```

| Variable        | Purpose                                         |
| --------------- | ----------------------------------------------- |
| `DB_USERNAME`   | MySQL username                                  |
| `DB_PASSWORD`   | MySQL password                                  |
| `MAIL_USERNAME` | Email address that sends booking confirmations  |
| `MAIL_PASSWORD` | App password for that email account             |
| `JWT_SECRET`    | Base64 key used to sign JWTs                    |

The app loads `.env` automatically when started from the `HospitalAppointmentSystem` folder. Real environment variables work too and take priority. `.env` is listed in `.gitignore`, so it will not be committed.

### 4. Run the application

From the `HospitalAppointmentSystem` folder:

```bash
mvn spring-boot:run
```

The API starts on `http://localhost:8080`.

### 5. Create an HOD account

For security, HODs cannot sign up through the API. Insert one directly into the database:

1. Generate a BCrypt hash of the password by running `PasswordHasher` (in `src/test/java/.../PasswordHasher.java`) and entering the password when prompted.
2. Insert the HOD row using that hash:

```sql
INSERT INTO hod (name, email, password, role, department)
VALUES ('Jane Smith', 'jane.smith@example.com', '<bcrypt-hash>', 'HOD', 'Cardiology');
```

## How it works

```
HOD  ──registers──▶  Doctor (with weekly 2 hour slots)
                         │
Patient ──views available slots──▶ books a slot ──▶ Appointment saved
                                                  └─▶ confirmation email sent
                         │
Doctor ──views schedule (booked appointments)
```

## API reference

All endpoints accept and return JSON. Every endpoint except `/auth/**` requires a token:

```
Authorization: Bearer <token>
```

| Method | Endpoint                         | Role      | Description                         |
| ------ | -------------------------------- | --------- | ----------------------------------- |
| POST   | `/auth/register`                 | Public    | Register a new patient              |
| POST   | `/auth/login`                    | Public    | Log in and receive a JWT            |
| GET    | `/patient/view-available-Slots`  | `PATIENT` | List all unbooked slots             |
| POST   | `/patient/book-Appointment`      | `PATIENT` | Book a slot by its id               |
| GET    | `/doctor/view-schedule`          | `DOCTOR`  | List the doctor's booked appointments |
| POST   | `/hod/register-Doctor`           | `HOD`     | Register a doctor with their slots  |

> Endpoint paths are case sensitive, so use them exactly as written above.

### Register a patient

`POST /auth/register`

```json
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "password": "password123",
  "phoneNumber": "08012345678"
}
```

The phone number must be exactly 11 digits. Email and phone number must be unique.

### Log in

`POST /auth/login` (works for patients, doctors and HODs)

```json
{
  "email": "john.doe@example.com",
  "password": "password123"
}
```

The response body is the JWT as plain text. Use it in the `Authorization` header for the endpoints below.

### Register a doctor (HOD only)

`POST /hod/register-Doctor`

```json
{
  "name": "Dr. Ben Carson",
  "email": "ben.carson@example.com",
  "password": "doctorPass1",
  "specialization": "Neurology",
  "schedule": [
    { "slot": { "day": "MONDAY", "timeSlots": "09:00 - 11:00" } },
    { "slot": { "day": "WEDNESDAY", "timeSlots": "13:00 - 15:00" } }
  ]
}
```

Rules for each slot:

- `day` is a day of the week (`MONDAY` to `SUNDAY`, case insensitive)
- `timeSlots` must use the format `HH:mm - HH:mm`
- The start time must be before the end time, and the slot must be exactly 2 hours

### View available slots (Patient only)

`GET /patient/view-available-Slots`

```json
[
  {
    "slotId": 1,
    "day": "MONDAY",
    "timeSlots": "09:00 - 11:00",
    "doctor": {
      "name": "Dr. Ben Carson",
      "specialization": "Neurology"
    }
  }
]
```

### Book an appointment (Patient only)

`POST /patient/book-Appointment`

```json
{
  "slotId": 1
}
```

On success the slot is marked as booked, the appointment is saved and a confirmation email is sent to the patient.

### View schedule (Doctor only)

`GET /doctor/view-schedule`

```json
[
  {
    "patient": { "name": "John Doe" },
    "slot": { "day": "MONDAY", "timeSlots": "09:00 - 11:00" }
  }
]
```

## Example session with curl

```bash
# 1. Register and log in as a patient
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john.doe@example.com","password":"password123","phoneNumber":"08012345678"}'

TOKEN=$(curl -s -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"john.doe@example.com","password":"password123"}')

# 2. See what is available and book a slot
curl http://localhost:8080/patient/view-available-Slots -H "Authorization: Bearer $TOKEN"

curl -X POST http://localhost:8080/patient/book-Appointment \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"slotId":1}'
```

## Running tests

```bash
mvn test
```

## Security notes

- Never commit `.env` or put real credentials in `application.properties`.
- `SecretKeyGenerator` (in the test sources) can generate a new JWT signing key.
