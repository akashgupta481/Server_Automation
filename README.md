# Server Automation Project

## Project Structure

- **src/test/java/com/automation/pages/**  
  Contains Page Object Model (POM) classes representing different pages of the web application, such as `HomePage`, `OrdersPage`, and `BasePage`.

- **src/test/java/com/automation/tests/**  
  Contains test classes that execute automated tests using the page objects, e.g., `GetAllOrdersTest`.

- **src/test/java/com/automation/utils/**  
  Utility classes for common functionalities like Excel file handling (`ExcelUtils`), configuration reading (`ConfigReader`), and WebDriver management (`DriverManager`).

- **src/test/resources/config/**  
  Contains configuration files such as `config.properties` which holds sensitive data like session IDs and application URLs.

- **src/test/resources/excel-files/**  
  Directory where Excel files are read from and saved to, e.g., `SalezMedia.xlsx`.

## Important Notes

- The `config.properties` file contains sensitive information and **should not be committed to version control**. It is recommended to add this file to `.gitignore`.

- The `config.properties` file must be present in the `src/test/resources/config/` directory with the following key details:
  - `application.url` - The URL of the application under test.
  - `session.id` - Session ID for authentication.
  - `start.page` - Starting page number for scraping orders.
  - `last.page` - Last page number for scraping orders.
  - `start.row.num` - Starting row number for Excel data writing.

## How to Use

1. Configure the `config.properties` file with appropriate values.
2. Run the test classes in `src/test/java/com/automation/tests/` to perform automated actions and export data to Excel.
3. Excel files will be saved in the `src/test/resources/excel-files/` directory.

## .gitignore Update

Make sure to add the following line to your `.gitignore` file to exclude the config file:

```
src/test/resources/config/config.properties
```

This prevents sensitive data from being pushed to the repository.
