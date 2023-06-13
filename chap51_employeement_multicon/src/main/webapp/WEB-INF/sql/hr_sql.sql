
old version

hr 데이터베이스 새로 만드는 방법(데이터 포함)

-- ALTER SESSION SET NLS_LANGUAGE=American;
-- ALTER SESSION SET NLS_TERRITORY=America;

-- 12c 버전 부터는 아래 문장 실행후 계정 생성 
 ALTER SESSION SET "_ORACLE_SCRIPT"=true; 
CREATE TABLE regions
    ( region_id      NUMBER CONSTRAINT  reg_id_pk PRIMARY KEY,
      region_name    VARCHAR2(25)
);

CREATE TABLE countries
    ( country_id      CHAR(2) CONSTRAINT country_c_id_pk PRIMARY KEY ,
      country_name    VARCHAR2(40),
      region_id       NUMBER CONSTRAINT countr_reg_fk
		          	  REFERENCES regions(region_id)
    )
    ORGANIZATION INDEX;

CREATE TABLE locations
    ( location_id    NUMBER(4) CONSTRAINT loc_id_pk PRIMARY KEY,
      street_address VARCHAR2(40),
      postal_code    VARCHAR2(12),
      city           VARCHAR2(30) CONSTRAINT loc_city_nn  NOT NULL,
      state_province VARCHAR2(25),
      country_id     CHAR(2) CONSTRAINT loc_c_id_fk
		        	 REFERENCES countries(country_id)
    ) ;

CREATE TABLE departments
    ( department_id    NUMBER(4) CONSTRAINT dept_id_pk PRIMARY KEY,
      department_name  VARCHAR2(30) CONSTRAINT  dept_name_nn  NOT NULL,
      manager_id       NUMBER(6),
      location_id      NUMBER(4) CONSTRAINT dept_loc_fk
        	  		   REFERENCES locations (location_id)
    ) ;

CREATE TABLE jobs
    ( job_id         VARCHAR2(10) CONSTRAINT job_id_pk PRIMARY KEY,
      job_title      VARCHAR2(35) CONSTRAINT     job_title_nn  NOT NULL,
      min_salary     NUMBER(6),
      max_salary     NUMBER(6)
    ) ;

create table job_grades
	( grade_level varchar2(3) CONSTRAINT job_gra_level_pk PRIMARY KEY,
	  lowest_sal number,
	  highest_sal number);

CREATE TABLE employees
    ( employee_id    NUMBER(6) CONSTRAINT emp_emp_id_pk PRIMARY KEY ,
      first_name     VARCHAR2(20),
      last_name      VARCHAR2(25) CONSTRAINT emp_last_name_nn  NOT NULL,
      email          VARCHAR2(25) CONSTRAINT emp_email_uk UNIQUE,
      phone_number   VARCHAR2(20),
      hire_date      DATE 		  CONSTRAINT  emp_hire_date_nn  NOT NULL,
      job_id         VARCHAR2(10) CONSTRAINT  emp_job_fk  REFERENCES jobs(job_id),
      salary         NUMBER(8,2)  CONSTRAINT  emp_salary_min CHECK (salary > 0),
      commission_pct NUMBER(2,2),
      manager_id     NUMBER(6) 	  CONSTRAINT  emp_manager_fk REFERENCES employees(employee_id),
      department_id  NUMBER(4) CONSTRAINT emp_dept_fk REFERENCES departments(department_id)
    ) ;

-- select * from tab;


ALTER TABLE departments
ADD (CONSTRAINT dept_mgr_fk FOREIGN KEY(manager_id) REFERENCES employees (employee_id)
    ) ;

CREATE TABLE job_history
    ( employee_id   NUMBER(6) CONSTRAINT    jhist_employee_nn  NOT NULL,
      start_date    DATE 	CONSTRAINT    jhist_start_date_nn  NOT NULL,
      end_date      DATE	CONSTRAINT    jhist_end_date_nn  NOT NULL,
      job_id        VARCHAR2(10) 	CONSTRAINT    jhist_job_nn  NOT NULL,
      department_id NUMBER(4),
      CONSTRAINT jhist_emp_id_st_date_pk PRIMARY KEY (employee_id, start_date),
      CONSTRAINT jhist_date_interval     CHECK (end_date > start_date),
      CONSTRAINT jhist_job_fk FOREIGN KEY (job_id) REFERENCES jobs(job_id),
      CONSTRAINT jhist_emp_fk FOREIGN KEY (employee_id) REFERENCES employees(employee_id),
      CONSTRAINT jhist_dept_fk FOREIGN KEY (department_id) REFERENCES departments(department_id)
    ) ;

CREATE SEQUENCE locations_seq
 START WITH     3300
 INCREMENT BY   100
 MAXVALUE       9900
 NOCACHE
 NOCYCLE;

CREATE SEQUENCE departments_seq
 START WITH     280
 INCREMENT BY   10
 MAXVALUE       9990
 NOCACHE
 NOCYCLE;

CREATE SEQUENCE employees_seq
 START WITH     207
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;


CREATE OR REPLACE VIEW emp_details_view
  (employee_id,
   job_id,
   manager_id,
   department_id,
   location_id,
   country_id,
   first_name,
   last_name,
   salary,
   commission_pct,
   department_name,
   job_title,
   city,
   state_province,
   country_name,
   region_name)
AS SELECT
  e.employee_id,
  e.job_id,
  e.manager_id,
  e.department_id,
  d.location_id,
  l.country_id,
  e.first_name,
  e.last_name,
  e.salary,
  e.commission_pct,
  d.department_name,
  j.job_title,
  l.city,
  l.state_province,
  c.country_name,
  r.region_name
FROM
  employees e,
  departments d,
  jobs j,
  locations l,
  countries c,
  regions r
WHERE e.department_id = d.department_id
  AND d.location_id = l.location_id
  AND l.country_id = c.country_id
  AND c.region_id = r.region_id
  AND j.job_id = e.job_id
WITH READ ONLY;

COMMIT;

SET VERIFY OFF
ALTER SESSION SET NLS_LANGUAGE=American;



INSERT INTO regions VALUES
        ( 1
        , 'Europe'
        );

INSERT INTO regions VALUES
        ( 2
        , 'Americas'
        );

INSERT INTO regions VALUES
        ( 3
        , 'Asia'
        );

INSERT INTO regions VALUES
        ( 4
        , 'Middle East and Africa'
        );




INSERT INTO countries VALUES
        ( 'US'
        , 'United States of America'
        , 2
        );

INSERT INTO countries VALUES
        ( 'CA'
        , 'Canada'
        , 2
        );

INSERT INTO countries VALUES
        ( 'UK'
        , 'United Kingdom'
        , 1
        );

INSERT INTO countries VALUES
        ( 'DE'
        , 'Germany'
        , 1
        );



INSERT INTO locations VALUES
        ( 1400
        , '2014 Jabberwocky Rd'
        , '26192'
        , 'Southlake'
        , 'Texas'
        , 'US'
        );

INSERT INTO locations VALUES
        ( 1500
        , '2011 Interiors Blvd'
        , '99236'
        , 'South San Francisco'
        , 'California'
        , 'US'
        );

INSERT INTO locations VALUES
        ( 1700
        , '2004 Charade Rd'
        , '98199'
        , 'Seattle'
        , 'Washington'
        , 'US'
        );

INSERT INTO locations VALUES
        ( 1800
        , '147 Spadina Ave'
        , 'M5V 2L7'
        , 'Toronto'
        , 'Ontario'
        , 'CA'
        );


INSERT INTO locations VALUES
        ( 2500
        , 'Magdalen Centre, The Oxford Science Park'
        , 'OX9 9ZB'
        , 'Oxford'
        , 'Oxford'
        , 'UK'
        );


ALTER TABLE departments
  DISABLE CONSTRAINT dept_mgr_fk;

INSERT INTO departments VALUES
        ( 10
        , 'Administration'
        , 200
        , 1700
        );

INSERT INTO departments VALUES
        ( 20
        , 'Marketing'
        , 201
        , 1800
        );


INSERT INTO departments VALUES
        ( 50
        , 'Shipping'
        , 124
        , 1500
        );

INSERT INTO departments VALUES
        ( 60
        , 'IT'
        , 103
        , 1400
        );

INSERT INTO departments VALUES
        ( 80
        , 'Sales'
        , 149
        , 2500
        );

INSERT INTO departments VALUES
        ( 90
        , 'Executive'
        , 100
        , 1700
        );


INSERT INTO departments VALUES
        ( 110
        , 'Accounting'
        , 205
        , 1700
        );


INSERT INTO departments VALUES
        ( 190
        , 'Contracting'
        , NULL
        , 1700
        );

INSERT INTO jobs VALUES
        ( 'AD_PRES'
        , 'President'
        , 20000
        , 40000
        );

INSERT INTO jobs VALUES
        ( 'AD_VP'
        , 'Administration Vice President'
        , 15000
        , 30000
        );

INSERT INTO jobs VALUES
        ( 'AD_ASST'
        , 'Administration Assistant'
        , 3000
        , 6000
        );


INSERT INTO jobs VALUES
        ( 'AC_MGR'
        , 'Accounting Manager'
        , 8200
        , 16000
        );

INSERT INTO jobs VALUES
        ( 'AC_ACCOUNT'
        , 'Public Accountant'
        , 4200
        , 9000
        );
INSERT INTO jobs VALUES
        ( 'SA_MAN'
        , 'Sales Manager'
        , 10000
        , 20000
        );

INSERT INTO jobs VALUES
        ( 'SA_REP'
        , 'Sales Representative'
        , 6000
        , 12000
        );


INSERT INTO jobs VALUES
        ( 'ST_MAN'
        , 'Stock Manager'
        , 5500
        , 8500
        );

INSERT INTO jobs VALUES
        ( 'ST_CLERK'
        , 'Stock Clerk'
        , 2000
        , 5000
        );


INSERT INTO jobs VALUES
        ( 'IT_PROG'
        , 'Programmer'
        , 4000
        , 10000
        );

INSERT INTO jobs VALUES
        ( 'MK_MAN'
        , 'Marketing Manager'
        , 9000
        , 15000
        );

INSERT INTO jobs VALUES
        ( 'MK_REP'
        , 'Marketing Representative'
        , 4000
        , 9000
        );


INSERT INTO employees VALUES
        ( 100
        , 'Steven'
        , 'King'
        , 'SKING'
        , '515.123.4567'
        , TO_DATE('17-JUN-1987', 'dd-MON-yyyy')
        , 'AD_PRES'
        , 24000
        , NULL
        , NULL
        , 90
        );

INSERT INTO employees VALUES
        ( 101
        , 'Neena'
        , 'Kochhar'
        , 'NKOCHHAR'
        , '515.123.4568'
        , TO_DATE('21-SEP-1989', 'dd-MON-yyyy')
        , 'AD_VP'
        , 17000
        , NULL
        , 100
        , 90
        );

INSERT INTO employees VALUES
        ( 102
        , 'Lex'
        , 'De Haan'
        , 'LDEHAAN'
        , '515.123.4569'
        , TO_DATE('13-JAN-1993', 'dd-MON-yyyy')
        , 'AD_VP'
        , 17000
        , NULL
        , 100
        , 90
        );

INSERT INTO employees VALUES
        ( 103
        , 'Alexander'
        , 'Hunold'
        , 'AHUNOLD'
        , '590.423.4567'
        , TO_DATE('03-JAN-1990', 'dd-MON-yyyy')
        , 'IT_PROG'
        , 9000
        , NULL
        , 102
        , 60
        );

INSERT INTO employees VALUES
        ( 104
        , 'Bruce'
        , 'Ernst'
        , 'BERNST'
        , '590.423.4568'
        , TO_DATE('21-MAY-1991', 'dd-MON-yyyy')
        , 'IT_PROG'
        , 6000
        , NULL
        , 103
        , 60
        );



INSERT INTO employees VALUES
        ( 107
        , 'Diana'
        , 'Lorentz'
        , 'DLORENTZ'
        , '590.423.5567'
        , TO_DATE('07-FEB-1999', 'dd-MON-yyyy')
        , 'IT_PROG'
        , 4200
        , NULL
        , 103
        , 60
        );


INSERT INTO employees VALUES
        ( 124
        , 'Kevin'
        , 'Mourgos'
        , 'KMOURGOS'
        , '650.123.5234'
        , TO_DATE('16-NOV-1999', 'dd-MON-yyyy')
        , 'ST_MAN'
        , 5800
        , NULL
        , 100
        , 50
        );


INSERT INTO employees VALUES
        ( 141
        , 'Trenna'
        , 'Rajs'
        , 'TRAJS'
        , '650.121.8009'
        , TO_DATE('17-OCT-1995', 'dd-MON-yyyy')
        , 'ST_CLERK'
        , 3500
        , NULL
        , 124
        , 50
        );

INSERT INTO employees VALUES
        ( 142
        , 'Curtis'
        , 'Davies'
        , 'CDAVIES'
        , '650.121.2994'
        , TO_DATE('29-JAN-1997', 'dd-MON-yyyy')
        , 'ST_CLERK'
        , 3100
        , NULL
        , 124
        , 50
        );

INSERT INTO employees VALUES
        ( 143
        , 'Randall'
        , 'Matos'
        , 'RMATOS'
        , '650.121.2874'
        , TO_DATE('15-MAR-1998', 'dd-MON-yyyy')
        , 'ST_CLERK'
        , 2600
        , NULL
        , 124
        , 50
        );

INSERT INTO employees VALUES
        ( 144
        , 'Peter'
        , 'Vargas'
        , 'PVARGAS'
        , '650.121.2004'
        , TO_DATE('09-JUL-1998', 'dd-MON-yyyy')
        , 'ST_CLERK'
        , 2500
        , NULL
        , 124
        , 50
        );



INSERT INTO employees VALUES
        ( 149
        , 'Eleni'
        , 'Zlotkey'
        , 'EZLOTKEY'
        , '011.44.1344.429018'
        , TO_DATE('29-JAN-2000', 'dd-MON-yyyy')
        , 'SA_MAN'
        , 10500
        , .2
        , 100
        , 80
        );


INSERT INTO employees VALUES
        ( 174
        , 'Ellen'
        , 'Abel'
        , 'EABEL'
        , '011.44.1644.429267'
        , TO_DATE('11-MAY-1996', 'dd-MON-yyyy')
        , 'SA_REP'
        , 11000
        , .30
        , 149
        , 80
        );


INSERT INTO employees VALUES
        ( 176
        , 'Jonathon'
        , 'Taylor'
        , 'JTAYLOR'
        , '011.44.1644.429265'
        , TO_DATE('24-MAR-1998', 'dd-MON-yyyy')
        , 'SA_REP'
        , 8600
        , .20
        , 149
        , 80
        );


INSERT INTO employees VALUES
        ( 178
        , 'Kimberely'
        , 'Grant'
        , 'KGRANT'
        , '011.44.1644.429263'
        , TO_DATE('24-MAY-1999', 'dd-MON-yyyy')
        , 'SA_REP'
        , 7000
        , .15
        , 149
        , NULL
        );


INSERT INTO employees VALUES
        ( 200
        , 'Jennifer'
        , 'Whalen'
        , 'JWHALEN'
        , '515.123.4444'
        , TO_DATE('17-SEP-1987', 'dd-MON-yyyy')
        , 'AD_ASST'
        , 4400
        , NULL
        , 101
        , 10
        );

INSERT INTO employees VALUES
        ( 201
        , 'Michael'
        , 'Hartstein'
        , 'MHARTSTE'
        , '515.123.5555'
        , TO_DATE('17-FEB-1996', 'dd-MON-yyyy')
        , 'MK_MAN'
        , 13000
        , NULL
        , 100
        , 20
        );

INSERT INTO employees VALUES
        ( 202
        , 'Pat'
        , 'Fay'
        , 'PFAY'
        , '603.123.6666'
        , TO_DATE('17-AUG-1997', 'dd-MON-yyyy')
        , 'MK_REP'
        , 6000
        , NULL
        , 201
        , 20
        );


INSERT INTO employees VALUES
        ( 205
        , 'Shelley'
        , 'Higgins'
        , 'SHIGGINS'
        , '515.123.8080'
        , TO_DATE('07-JUN-1994', 'dd-MON-yyyy')
        , 'AC_MGR'
        , 12000
        , NULL
        , 101
        , 110
        );

INSERT INTO employees VALUES
        ( 206
        , 'William'
        , 'Gietz'
        , 'WGIETZ'
        , '515.123.8181'
        , TO_DATE('07-JUN-1994', 'dd-MON-yyyy')
        , 'AC_ACCOUNT'
        , 8300
        , NULL
        , 205
        , 110
        );

insert into job_grades
values('A',1000, 2999);

insert into job_grades
values('B',3000, 5999);

insert into job_grades
values('C',6000, 9999);

insert into job_grades
values('D',10000, 14999);

insert into job_grades
values('E',15000, 24999);

insert into job_grades
values('F',25000, 40000);


INSERT INTO job_history
VALUES (102
       , TO_DATE('13-JAN-1993', 'dd-MON-yyyy')
       , TO_DATE('24-JUL-1998', 'dd-MON-yyyy')
       , 'IT_PROG'
       , 60);

INSERT INTO job_history
VALUES (101
       , TO_DATE('21-SEP-1989', 'dd-MON-yyyy')
       , TO_DATE('27-OCT-1993', 'dd-MON-yyyy')
       , 'AC_ACCOUNT'
       , 110);

INSERT INTO job_history
VALUES (101
       , TO_DATE('28-OCT-1993', 'dd-MON-yyyy')
       , TO_DATE('15-MAR-1997', 'dd-MON-yyyy')
       , 'AC_MGR'
       , 110);

INSERT INTO job_history
VALUES (201
       , TO_DATE('17-FEB-1996', 'dd-MON-yyyy')
       , TO_DATE('19-DEC-1999', 'dd-MON-yyyy')
       , 'MK_REP'
       , 20);


INSERT INTO job_history
VALUES  (200
        , TO_DATE('17-SEP-1987', 'dd-MON-yyyy')
        , TO_DATE('17-JUN-1993', 'dd-MON-yyyy')
        , 'AD_ASST'
        , 90
        );

INSERT INTO job_history
VALUES  (176
        , TO_DATE('24-MAR-1998', 'dd-MON-yyyy')
        , TO_DATE('31-DEC-1998', 'dd-MON-yyyy')
        , 'SA_REP'
        , 80
        );

INSERT INTO job_history
VALUES  (176
        , TO_DATE('01-JAN-1999', 'dd-MON-yyyy')
        , TO_DATE('31-DEC-1999', 'dd-MON-yyyy')
        , 'SA_MAN'
        , 80
        );

INSERT INTO job_history
VALUES  (200
        , TO_DATE('01-JUL-1994', 'dd-MON-yyyy')
        , TO_DATE('31-DEC-1998', 'dd-MON-yyyy')
        , 'AC_ACCOUNT'
        , 90
        );

ALTER TABLE departments
  ENABLE CONSTRAINT dept_mgr_fk;

COMMIT;

CREATE INDEX emp_department_ix
       ON employees (department_id);

CREATE INDEX emp_job_ix
       ON employees (job_id);

CREATE INDEX emp_manager_ix
       ON employees (manager_id);

CREATE INDEX emp_name_ix
       ON employees (last_name, first_name);

CREATE INDEX dept_location_ix
       ON departments (location_id);

CREATE INDEX jhist_job_ix
       ON job_history (job_id);

CREATE INDEX jhist_employee_ix
       ON job_history (employee_id);

CREATE INDEX jhist_department_ix
       ON job_history (department_id);

CREATE INDEX loc_city_ix
       ON locations (city);

CREATE INDEX loc_state_province_ix
       ON locations (state_province);

CREATE INDEX loc_country_ix
       ON locations (country_id);

COMMIT;

CREATE OR REPLACE PROCEDURE secure_dml
IS
BEGIN
  IF TO_CHAR (SYSDATE, 'HH24:MI') NOT BETWEEN '08:00' AND '18:00'
        OR TO_CHAR (SYSDATE, 'DY') IN ('SAT', 'SUN') THEN
	RAISE_APPLICATION_ERROR (-20205,
		'You may only make changes during normal office hours');
  END IF;
END secure_dml;
/

CREATE OR REPLACE TRIGGER secure_employees
  BEFORE INSERT OR UPDATE OR DELETE ON employees
BEGIN
  secure_dml;
END secure_employees;
/

ALTER TRIGGER secure_employees DISABLE;

REM **************************************************************************
REM procedure to add a row to the JOB_HISTORY table and row trigger
REM to call the procedure when data is updated in the job_id or
REM department_id columns in the EMPLOYEES table:

CREATE OR REPLACE PROCEDURE add_job_history
  (  p_emp_id          job_history.employee_id%type
   , p_start_date      job_history.start_date%type
   , p_end_date        job_history.end_date%type
   , p_job_id          job_history.job_id%type
   , p_department_id   job_history.department_id%type
   )
IS
BEGIN
  INSERT INTO job_history (employee_id, start_date, end_date,
                           job_id, department_id)
    VALUES(p_emp_id, p_start_date, p_end_date, p_job_id, p_department_id);
END add_job_history;
/

CREATE OR REPLACE TRIGGER update_job_history
  AFTER UPDATE OF job_id, department_id ON employees
  FOR EACH ROW
BEGIN
  add_job_history(:old.employee_id, :old.hire_date, sysdate,
                  :old.job_id, :old.department_id);
END;
/

COMMIT;
COMMENT ON TABLE regions
IS 'Regions table that contains region numbers and names. Contains 4 rows; references with the Countries table.'

COMMENT ON COLUMN regions.region_id
IS 'Primary key of regions table.'

COMMENT ON COLUMN regions.region_name
IS 'Names of regions. Locations are in the countries of these regions.'

COMMENT ON TABLE locations
IS 'Locations table that contains specific address of a specific office,
warehouse, and/or production site of a company. Does not store addresses /
locations of customers. Contains 23 rows; references with the
departments and countries tables. ';

COMMENT ON COLUMN locations.location_id
IS 'Primary key of locations table';

COMMENT ON COLUMN locations.street_address
IS 'Street address of an office, warehouse, or production site of a company.
Contains building number and street name';

COMMENT ON COLUMN locations.postal_code
IS 'Postal code of the location of an office, warehouse, or production site
of a company. ';

COMMENT ON COLUMN locations.city
IS 'A not null column that shows city where an office, warehouse, or
production site of a company is located. ';

COMMENT ON COLUMN locations.state_province
IS 'State or Province where an office, warehouse, or production site of a
company is located.';

COMMENT ON COLUMN locations.country_id
IS 'Country where an office, warehouse, or production site of a company is
located. Foreign key to country_id column of the countries table.';


REM *********************************************

COMMENT ON TABLE departments
IS 'Departments table that shows details of departments where employees
work. Contains 27 rows; references with locations, employees, and job_history tables.';

COMMENT ON COLUMN departments.department_id
IS 'Primary key column of departments table.';

COMMENT ON COLUMN departments.department_name
IS 'A not null column that shows name of a department. Administration,
Marketing, Purchasing, Human Resources, Shipping, IT, Executive, Public
Relations, Sales, Finance, and Accounting. ';

COMMENT ON COLUMN departments.manager_id
IS 'Manager_id of a department. Foreign key to employee_id column of employees table. The manager_id column of the employee table references this column.';

COMMENT ON COLUMN departments.location_id
IS 'Location id where a department is located. Foreign key to location_id column of locations table.';


REM *********************************************

COMMENT ON TABLE job_history
IS 'Table that stores job history of the employees. If an employee
changes departments within the job or changes jobs within the department,
new rows get inserted into this table with old job information of the
employee. Contains a complex primary key: employee_id+start_date.
Contains 25 rows. References with jobs, employees, and departments tables.';

COMMENT ON COLUMN job_history.employee_id
IS 'A not null column in the complex primary key employee_id+start_date.
Foreign key to employee_id column of the employee table';

COMMENT ON COLUMN job_history.start_date
IS 'A not null column in the complex primary key employee_id+start_date.
Must be less than the end_date of the job_history table. (enforced by
constraint jhist_date_interval)';

COMMENT ON COLUMN job_history.end_date
IS 'Last day of the employee in this job role. A not null column. Must be
greater than the start_date of the job_history table.
(enforced by constraint jhist_date_interval)';

COMMENT ON COLUMN job_history.job_id
IS 'Job role in which the employee worked in the past; foreign key to
job_id column in the jobs table. A not null column.';

COMMENT ON COLUMN job_history.department_id
IS 'Department id in which the employee worked in the past; foreign key to deparment_id column in the departments table';


REM *********************************************

COMMENT ON TABLE countries
IS 'country table. Contains 25 rows. References with locations table.';

COMMENT ON COLUMN countries.country_id
IS 'Primary key of countries table.';

COMMENT ON COLUMN countries.country_name
IS 'Country name';

COMMENT ON COLUMN countries.region_id
IS 'Region ID for the country. Foreign key to region_id column in the departments table.';

REM *********************************************

COMMENT ON TABLE jobs
IS 'jobs table with job titles and salary ranges. Contains 19 rows.
References with employees and job_history table.';

COMMENT ON COLUMN jobs.job_id
IS 'Primary key of jobs table.';

COMMENT ON COLUMN jobs.job_title
IS 'A not null column that shows job title, e.g. AD_VP, FI_ACCOUNTANT';

COMMENT ON COLUMN jobs.min_salary
IS 'Minimum salary for a job title.';

COMMENT ON COLUMN jobs.max_salary
IS 'Maximum salary for a job title';

REM *********************************************

COMMENT ON TABLE employees
IS 'employees table. Contains 107 rows. References with departments,
jobs, job_history tables. Contains a self reference.';

COMMENT ON COLUMN employees.employee_id
IS 'Primary key of employees table.';

COMMENT ON COLUMN employees.first_name
IS 'First name of the employee. A not null column.';

COMMENT ON COLUMN employees.last_name
IS 'Last name of the employee. A not null column.';

COMMENT ON COLUMN employees.email
IS 'Email id of the employee';

COMMENT ON COLUMN employees.phone_number
IS 'Phone number of the employee; includes country code and area code';

COMMENT ON COLUMN employees.hire_date
IS 'Date when the employee started on this job. A not null column.';

COMMENT ON COLUMN employees.job_id
IS 'Current job of the employee; foreign key to job_id column of the
jobs table. A not null column.';

COMMENT ON COLUMN employees.salary
IS 'Monthly salary of the employee. Must be greater
than zero (enforced by constraint emp_salary_min)';

COMMENT ON COLUMN employees.commission_pct
IS 'Commission percentage of the employee; Only employees in sales
department elgible for commission percentage';

COMMENT ON COLUMN employees.manager_id
IS 'Manager id of the employee; has same domain as manager_id in
departments table. Foreign key to employee_id column of employees table.
(useful for reflexive joins and CONNECT BY query)';

COMMENT ON COLUMN employees.department_id
IS 'Department id where employee works; foreign key to department_id
column of the departments table';

COMMIT;
EXECUTE dbms_stats.gather_table_stats ('HR','COUNTRIES');
EXECUTE dbms_stats.gather_table_stats ('HR','DEPARTMENTS');
EXECUTE dbms_stats.gather_table_stats ('HR','EMPLOYEES');
EXECUTE dbms_stats.gather_table_stats ('HR','JOBS');
EXECUTE dbms_stats.gather_table_stats ('HR','JOB_GRADES');
EXECUTE dbms_stats.gather_table_stats ('HR','JOB_HISTORY');
EXECUTE dbms_stats.gather_table_stats ('HR','LOCATIONS');
EXECUTE dbms_stats.gather_table_stats ('HR','REGIONS');

COMMIT;