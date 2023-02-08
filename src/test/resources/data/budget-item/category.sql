INSERT INTO categories (category_id, name, user_id, parent_category_category_id)
VALUES (1, 'parentCat', 1, null);
INSERT INTO categories (category_id, name, user_id, parent_category_category_id)
VALUES (2, 'childCat', 1, 1);