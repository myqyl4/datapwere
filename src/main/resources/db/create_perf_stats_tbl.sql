TRUNCATE TABLE HR.PERF_STATS REUSE STORAGE;

DELETE FROM HR.PERF_STATS;

commit;


DROP TABLE HR.PERF_STATS cascade constraints purge;

commit;



CREATE TABLE HR.PERF_STATS(
         id          NUMBER(20) NOT NULL,
         startTime      NUMBER(20),
         endTime      NUMBER(20),
         duration      NUMBER(20),
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


ALTER TABLE HR.PERF_STATS ADD (CONSTRAINT HR.PERF_STATS_pk PRIMARY KEY (id));

commit;

CREATE SEQUENCE HR.PERF_STATS_seq;

commit;


CREATE OR REPLACE TRIGGER HR.PERF_STATS_trggr 
BEFORE INSERT ON HR.PERF_STATS 
FOR EACH ROW
WHEN (new.id IS NULL)
BEGIN
  SELECT HR.PERF_STATS_seq.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/

--ALTER TABLE HR.PERF_STATS MODIFY id GENERATED ALWAYS AS IDENTITY;

--commit;



ALTER TABLE HE.PERF_STATS ADD (CONSTRAINT perf_stats_pk PRIMARY KEY (id));

commit;

ALTER TABLE HR.PERF_STATS  ADD filename varchar2(255);

commit;


ALTER SEQUENCE HR.PERF_STATS_seq INCREMENT BY 1 MINVALUE 0;
