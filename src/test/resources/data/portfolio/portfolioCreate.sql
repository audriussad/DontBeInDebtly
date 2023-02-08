--Categories
INSERT INTO categories (category_id, name, user_id, parent_category_category_id)
VALUES (1, 'parentCat1', 1, null);
INSERT INTO categories (category_id, name, user_id, parent_category_category_id)
VALUES (2, 'childCat11', 1, 1);
INSERT INTO categories (category_id, name, user_id, parent_category_category_id)
VALUES (3, 'childCat12', 1, 1);

INSERT INTO categories (category_id, name, user_id, parent_category_category_id)
VALUES (4, 'parentCat2', 1, null);
INSERT INTO categories (category_id, name, user_id, parent_category_category_id)
VALUES (5, 'childCat21', 1, 4);

INSERT INTO categories (category_id, name, user_id, parent_category_category_id)
VALUES (6, 'childCat22', 2, 4);

--Budget Items
INSERT INTO budget (id, date, planned_amount, user_id ,category_category_id)
VALUES (1, "2023-02-02", 50.00, 1, 2);
INSERT INTO budget (id, date, planned_amount, user_id ,category_category_id)
VALUES (2, "2023-02-02", 40.00, 1, 3);
INSERT INTO budget (id, date, planned_amount, user_id ,category_category_id)
VALUES (3, "2023-02-02", 420.69, 1, 5);

--Transactions
INSERT INTO transactions (id, amount, created_at, date,  user_id, category_category_id)
VALUES (1, 20.00, "2023-02-02 14:44:08.590432", "2023-02-02", 1, 2);
INSERT INTO transactions (id, amount, created_at, date,  user_id, category_category_id)
VALUES (2, 10.00, "2023-02-02 14:44:08.590432", "2023-02-02", 1, 2);
--This belongs to user 2 and will be ignored if program works as it should
INSERT INTO transactions (id, amount, created_at, date,  user_id, category_category_id)
VALUES (3, 20.00, "2023-02-02 14:44:08.590432", "2023-02-02", 2, 2);

INSERT INTO transactions (id, amount, created_at, date,  user_id, category_category_id)
VALUES (4, 5.00, "2023-02-02 14:44:08.590432", "2023-02-02", 1, 3);

INSERT INTO transactions (id, amount, created_at, date,  user_id, category_category_id)
VALUES (5, 420.69, "2023-02-02 14:44:08.590432", "2023-02-02", 1, 5);