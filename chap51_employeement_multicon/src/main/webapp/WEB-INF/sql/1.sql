select e.employee_id, e.first_name, e.last_name, e.email, e.phone_number, e.hire_date, e.salary, 
e.job_id, j.job_title, e.department_id, d.department_name, 
d.location_id, l.state_province, l.city, 
l.country_id, c.country_name
from employees e left outer join jobs j on e.job_id = j.job_id
inner join departments d on e.department_id = d.department_id
left outer join locations l on d.location_id = l.location_id
left outer join countries c on l.country_id = c.country_id;