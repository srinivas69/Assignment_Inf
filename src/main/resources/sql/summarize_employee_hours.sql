CREATE OR REPLACE PROCEDURE summarize_employee_hours IS
BEGIN
DELETE FROM employee_hours_summary;

INSERT INTO employee_hours_summary (employee_id, total_hours_worked)
SELECT employee_id, SUM(hours_worked)
FROM employee_work_hours
GROUP BY employee_id;

COMMIT;
END summarize_employee_hours;
/