DROP TABLE IF EXISTS Accounts;

DROP TABLE IF EXISTS "customer";

CREATE TABLE "customer" (
  "customer_id" INT AUTO_INCREMENT PRIMARY KEY,
  "name" VARCHAR(100) NOT NULL,
  "email" VARCHAR(100) NOT NULL,
  "mobile_number" VARCHAR(20) NOT NULL,
  "create_dt" DATE DEFAULT NULL
);

INSERT INTO "customer" ("name", "email", "mobile_number", "create_dt") VALUES ('Madhav', 'madhav.singh@gmail.com', '9987899878', CURDATE());



DROP TABLE IF EXISTS "accounts";


CREATE TABLE "accounts" (
  "account_number" INT AUTO_INCREMENT PRIMARY KEY,
  "customer_id" INT NOT NULl,
  "account_type" VARCHAR(100) NOT NULL,
  "branch_address" VARCHAR(200) NOT NULL,
  "create_dt" DATE DEFAULT NULL
);


INSERT INTO "accounts" ("customer_id", "account_type", "branch_address", "create_dt") VALUES (123, 'Savings', 'Pratap Nagar Udapur', CURDATE());
