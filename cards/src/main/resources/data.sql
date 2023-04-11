DROP TABLE IF EXISTS "cards";

CREATE TABLE "cards" (
	"card_id" INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	"card_number" varchar(100) NOT NULL,
	"customer_id" INT NOT NULL,
	"card_type" varchar(100) NOT NULL,
	"total_limit" INT NOT NULL,
	"amount_used" INT NOT NULL,
	"available_amount" INT NOT NULL,
	"create_dt" DATE DEFAULT NULL
);

INSERT INTO "cards"("card_number", "customer_id", "card_type", "total_limit", "amount_used", "available_amount", "create_dt") 
VALUES('3232323423', 1, 'Credit', 10000, 500, 9500, CURDATE());

INSERT INTO "cards"("card_number", "customer_id", "card_type", "total_limit", "amount_used", "available_amount", "create_dt") 
VALUES('54353545353', 1, 'Credit', 7500, 500, 7000, CURDATE());

INSERT INTO "cards"("card_number", "customer_id", "card_type", "total_limit", "amount_used", "available_amount", "create_dt") 
VALUES('3223423', 1, 'Credit', 20000, 4000, 16000, CURDATE());


