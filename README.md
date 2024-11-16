# Hospital Management System (HMS)

[HMS-Application Link](https://github.com/KCCHONG1997/HMSApp)
This project is a group assignment for Nanyang Technological University's (NTU) SC2002 module on Object-Oriented Programming. The HMS application aims to automate various hospital operations, including patient management, appointment scheduling, staff management, and billing.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Dependencies](#dependencies)
- [Configuration](#configuration)
- [Troubleshooting](#troubleshooting)
- [Contributors](#contributors)
- [License](#license)
- [Disclaimer](#disclaimer)

## Introduction

The HMS application is designed to streamline hospital administrative processes by providing an integrated platform for managing patients, appointments, staff, and billing. This system enhances efficiency and improves patient care by automating routine tasks and centralizing information.

## Features

- **Patient Management:** Register and maintain patient records.
- **Appointment Scheduling:** Schedule, reschedule, and cancel appointments.
- **Staff Management:** Manage staff information and roles.

## Installation

To set up the HMS application locally:

1. **Clone the repository:**

   ```bash
   git clone https://github.com/KCCHONG1997/HMSApp.git
   ```

2. **Navigate to the project directory:**
   ```bash
   cd HMSApp
   ```
3. **Compile the application:**

   ```bash
   javac -d bin src\HMSApp\HMSMain.java
   ```

4. **Run the application:**
   ```bash
   java -cp bin HMSApp.HMSMain
   ```

## Usage

Upon running the application, you will be presented with a menu to navigate through different functionalities such as managing patients, scheduling appointments, handling staff information, and processing billing.

## Dependencies

- **Java Development Kit (JDK):** Ensure that JDK version 22 is installed on your system.

## Configuration

No additional configuration is required. The application runs with default settings suitable for a local environment.

## Troubleshooting

- **Compilation Errors:**

  Ensure that all Java source files are present in the `src` directory and that you have the correct JDK version installed. (JDK - 22)

- **Runtime Issues:**

  Verify that the compiled classes are in the `bin` directory and that you're running the `Main` class with the correct classpath.

## Contributors

This project was developed by a group of students as part of the SC2002 module at NTU. The contributors include:

- [KCCHONG1997](https://github.com/KCCHONG1997)
- [TanShiQi0802](https://github.com/TanShiQi0802)
- [hngkhai](https://github.com/hngkhai)
- [meiyeedope](https://github.com/meiyeedope)
- [Liuyzaz](https://github.com/Liuyzaz)

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Disclaimer

This project was developed as part of the SC2002 module on Object-Oriented Programming at Nanyang Technological University (NTU). The authors are undergraduate students, and NTU is not affiliated with the open-source release of this software.
