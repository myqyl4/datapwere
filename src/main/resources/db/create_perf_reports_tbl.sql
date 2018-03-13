TRUNCATE TABLE HR.PERF_REPORTS REUSE STORAGE;

DELETE FROM HR.PERF_REPORTS;

commit;


DROP TABLE HR.PERF_REPORTS cascade constraints purge;

commit;



CREATE TABLE HR.PERF_REPORTS(
         id          NUMBER(20) NOT NULL,
         totalRecords      NUMBER(20),
         averageDuration      NUMBER(20),
         longestDuration      NUMBER(20),
         shortestDuration      NUMBER(20),
         totalDuration      NUMBER(20),
         operationType        VARCHAR2(250),
         strategy        VARCHAR2(250),
         filename  VARCHAR2(255),
         runDate   DATE DEFAULT (sysdate),
         itemCount   NUMBER(5),
         createdBy   VARCHAR2(50),
         created   DATE DEFAULT (sysdate),
         modifiedBy   VARCHAR2(50),
         modified   DATE DEFAULT (sysdate)
         );
         

commit;


ALTER TABLE HR.PERF_REPORTS ADD (CONSTRAINT HR.PERF_REPORTS_pk PRIMARY KEY (id));

commit;

CREATE SEQUENCE HR.PERF_REPORTS_seq;

commit;


CREATE OR REPLACE TRIGGER HR.PERF_REPORTS_trggr 
BEFORE INSERT ON HR.PERF_REPORTS 
FOR EACH ROW
WHEN (new.id IS NULL)
BEGIN
  SELECT HR.PERF_REPORTS_seq.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/

--ALTER TABLE HR.PERF_REPORTS MODIFY id GENERATED ALWAYS AS IDENTITY;

--commit;



ALTER SEQUENCE HR.PERF_REPORTS_seq INCREMENT BY 1 MINVALUE 0;

commit;
