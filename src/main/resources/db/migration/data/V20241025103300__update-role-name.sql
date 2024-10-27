UPDATE role
SET name = CASE
               WHEN id = 1 THEN 'ROLE_ADMIN'
               WHEN id = 2 THEN 'ROLE_USER'
    END
WHERE id IN (1, 2);
