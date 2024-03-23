SELECT DISTINCT id,
                MAX(rev) OVER (PARTITION BY id), FIRST_VALUE(content) OVER (PARTITION BY id ORDER BY rev DESC
    )
FROM YourTable