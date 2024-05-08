INSERT INTO company (company_code, company_name, company_logo, company_registration_num, company_accident_manage_num, created_at, updated_at)
VALUES ('SFY001KOR', 'SSAFY', 'dummy', '123-45-67890', '123-45-67890-1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('SAM001KOR', 'SAMSUNG', 'dummy', '177-77-77777', '177-77-77777-7', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO employee (employee_num, company_code, employee_name, employee_title, employee_team, employee_birthday, employee_thum, employee_join_date, employee_leave_date, created_at, updated_at)
VALUES
    ('202100001', 'SFY001KOR', '김진명', '대리', '개발팀', '1996-02-23', 'default', '2021-07-12', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202000001', 'SFY001KOR', '오유진', '사원', '개발팀', '1975-06-08', 'default', '2023-12-11', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('199500001', 'SFY001KOR', '오민상', '과장', '생산기술팀', '1994-01-22', 'default', '2018-09-08', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('200500001', 'SFY001KOR', '한지훈', '사원', '영업팀', '1967-03-12', 'default', '2005-11-20', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201500001', 'SFY001KOR', '장정민', '사원', '개발팀', '1996-08-20', 'default', '2015-09-20', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('200500002', 'SFY001KOR', '김영철', '대리', '품질관리팀', '1970-12-01', 'default', '2005-06-25', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('200700001', 'SFY001KOR', '한건희', '대리', '생산기술팀', '1959-03-27', 'default', '2007-08-21', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202100002', 'SFY001KOR', '정형찬', '사원', '개발팀', '1972-03-17', 'default', '2021-02-23', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('200400001', 'SFY001KOR', '이지훈', '사원', '경영지원팀', '1960-11-04', 'default', '2004-06-02', '2013-06-07', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202200001', 'SFY001KOR', '임정민', '사원', '영업팀', '1984-02-26', 'default', '2022-10-16', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('198900001', 'SFY001KOR', '오승호', '사원', '영업팀', '1961-03-19', 'default', '1989-08-12', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201100001', 'SFY001KOR', '박영철', '부장', '개발팀', '1988-06-05', 'default', '2011-10-04', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('199500002', 'SFY001KOR', '이서연', '대리', '개발팀', '1973-10-23', 'default', '1995-05-11', '1998-07-11', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202300001', 'SFY001KOR', '한예은', '대리', '영업팀', '1986-07-18', 'default', '2023-05-16', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('198500001', 'SFY001KOR', '박현진', '사원', '품질관리팀', '1967-11-01', 'default', '1985-07-18', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('200900001', 'SFY001KOR', '임준호', '대리', '개발팀', '1961-07-17', 'default', '2009-04-06', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201000001', 'SFY001KOR', '정나라', '사원', '경영지원팀', '1975-12-06', 'default', '2010-02-07', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202300002', 'SFY001KOR', '오하은', '과장', '영업팀', '1998-09-24', 'default', '2023-11-09', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('200300001', 'SFY001KOR', '임빈', '대리', '영업팀', '1966-04-05', 'default', '2003-01-31', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('200200001', 'SFY001KOR', '임혜진', '사원', '경영지원팀', '1959-05-28', 'default', '2002-01-25', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('200800001', 'SFY001KOR', '윤승호', '사원', '생산기술팀', '1984-12-27', 'default', '2008-05-19', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('200700002', 'SFY001KOR', '한유진', '과장', '품질관리팀', '1981-06-18', 'default', '2007-04-25', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201800001', 'SFY001KOR', '정재용', '사원', '품질관리팀', '1997-02-27', 'default', '2018-04-04', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('200700003', 'SFY001KOR', '최서연', '사원', '경영지원팀', '1986-05-10', 'default', '2007-02-16', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201100002', 'SFY001KOR', '박재인', '차장', '품질관리팀', '1980-08-05', 'default', '2011-07-28', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('200600001', 'SFY001KOR', '장민철', '사원', '생산기술팀', '1987-11-29', 'default', '2006-09-09', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201700001', 'SFY001KOR', '이하은', '사원', '영업팀', '1972-08-17', 'default', '2017-04-07', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201300001', 'SFY001KOR', '오태원', '사원', '영업팀', '1964-06-07', 'default', '2013-07-09', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201500002', 'SFY001KOR', '임재용', '대리', '경영지원팀', '1961-08-13', 'default', '2015-08-10', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201900001', 'SFY001KOR', '박혜서', '대리', '생산기술팀', '1991-07-21', 'default', '2019-01-12', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('200600002', 'SFY001KOR', '조태원', '대리', '개발팀', '1969-03-03', 'default', '2006-03-20', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('199700001', 'SFY001KOR', '최민상', '대리', '개발팀', '1969-07-01', 'default', '1997-02-05', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201900002', 'SFY001KOR', '박혜영', '사원', '생산기술팀', '2000-08-09', 'default', '2019-12-21', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('198700001', 'SFY001KOR', '윤민수', '사원', '개발팀', '1969-03-09', 'default', '1987-05-02', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('198900002', 'SFY001KOR', '장건희', '사원', '품질관리팀', '1971-09-03', 'default', '1989-10-27', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('200600003', 'SFY001KOR', '오영철', '사원', '개발팀', '1980-07-16', 'default', '2006-06-19', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('200900002', 'SFY001KOR', '장혜서', '대리', '경영지원팀', '1971-02-17', 'default', '2009-12-19', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201800002', 'SFY001KOR', '정재명', '사원', '생산기술팀', '1979-02-20', 'default', '2018-01-01', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201400001', 'SFY001KOR', '정진명', '사원', '개발팀', '1962-08-12', 'default', '2014-12-04', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201300002', 'SFY001KOR', '장재용', '대리', '경영지원팀', '1993-09-25', 'default', '2013-02-05', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('199100001', 'SFY001KOR', '김형찬', '대리', '생산기술팀', '1959-01-29', 'default', '1991-05-09', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202000002', 'SFY001KOR', '한근혜', '사원', '생산기술팀', '2000-05-14', 'default', '2020-03-18', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202200002', 'SFY001KOR', '박혜영', '대리', '품질관리팀', '1997-03-23', 'default', '2022-09-15', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201600001', 'SFY001KOR', '임재명', '과장', '생산기술팀', '1998-02-05', 'default', '2016-07-10', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201800003', 'SFY001KOR', '최재인', '과장', '생산기술팀', '1969-01-03', 'default', '2018-02-05', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202100003', 'SFY001KOR', '윤재명', '사원', '경영지원팀', '2001-08-30', 'default', '2021-12-22', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202200003', 'SFY001KOR', '장종건', '대리', '생산기술팀', '1980-06-20', 'default', '2022-08-12', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202100004', 'SFY001KOR', '박영철', '과장', '경영지원팀', '1964-04-23', 'default', '2021-02-12', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('200100001', 'SFY001KOR', '정현진', '사원', '개발팀', '1975-03-09', 'default', '2001-04-13', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201100003', 'SFY001KOR', '최나라', '사원', '경영지원팀', '1991-11-16', 'default', '2011-11-25', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('200600004', 'SFY001KOR', '한현비', '과장', '영업팀', '1962-12-20', 'default', '2006-05-08', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201100004', 'SFY001KOR', '오영마', '과장', '생산기술팀', '1987-11-20', 'default', '2011-11-28', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201600002', 'SFY001KOR', '오재용', '부장', '생산기술팀', '1992-02-20', 'default', '2016-09-12', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201100005', 'SFY001KOR', '윤지훈', '대리', '품질관리팀', '1985-07-13', 'default', '2011-06-24', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('200300002', 'SFY001KOR', '임지아', '대리', '영업팀', '1984-12-17', 'default', '2003-02-02', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202100005', 'SFY001KOR', '김태원', '사원', '개발팀', '2002-12-03', 'default', '2021-07-03', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('199900001', 'SFY001KOR', '윤민수', '과장', '생산기술팀', '1981-11-16', 'default', '1999-10-11', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202000003', 'SFY001KOR', '윤명박', '대리', '품질관리팀', '1979-05-28', 'default', '2020-02-10', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('200000001', 'SFY001KOR', '윤혜서', '대리', '생산기술팀', '1972-01-18', 'default', '2000-07-01', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('200900003', 'SFY001KOR', '임서연', '대리', '개발팀', '1981-02-08', 'default', '2009-12-23', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202000004', 'SFY001KOR', '김영철', '과장', '생산기술팀', '1999-10-12', 'default', '2020-03-14', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201200001', 'SFY001KOR', '김유진', '대리', '개발팀', '1984-05-08', 'default', '2012-06-21', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('200700004', 'SFY001KOR', '박건', '대리', '개발팀', '1961-02-04', 'default', '2007-05-11', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('200500003', 'SFY001KOR', '오형찬', '대리', '영업팀', '1977-06-08', 'default', '2005-04-04', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201400002', 'SFY001KOR', '한지아', '대리', '개발팀', '1994-09-10', 'default', '2014-08-12', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('200900004', 'SFY001KOR', '최진명', '과장', '영업팀', '1983-09-23', 'default', '2009-08-01', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201800004', 'SFY001KOR', '한준호', '차장', '경영지원팀', '1995-06-14', 'default', '2018-08-16', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201700002', 'SFY001KOR', '정지아', '사원', '개발팀', '1995-04-02', 'default', '2017-12-10', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('199100002', 'SFY001KOR', '윤유진', '사원', '생산기술팀', '1972-03-02', 'default', '1991-05-01', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('200400002', 'SFY001KOR', '오하은', '대리', '생산기술팀', '1984-05-03', 'default', '2004-05-26', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201000002', 'SFY001KOR', '오혜서', '과장', '영업팀', '1963-01-02', 'default', '2010-08-09', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201700003', 'SFY001KOR', '장명박', '사원', '생산기술팀', '1995-11-28', 'default', '2017-11-09', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201500003', 'SFY001KOR', '오재용', '과장', '경영지원팀', '1996-10-12', 'default', '2015-08-19', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202100006', 'SFY001KOR', '조예은', '사원', '영업팀', '2000-04-19', 'default', '2021-03-01', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201800005', 'SFY001KOR', '박혜영', '과장', '개발팀', '1988-01-31', 'default', '2018-06-26', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202300003', 'SFY001KOR', '최태원', '과장', '영업팀', '2003-02-06', 'default', '2023-07-01', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('199800001', 'SFY001KOR', '박민상', '사원', '생산기술팀', '1978-05-16', 'default', '1998-12-23', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201200002', 'SFY001KOR', '최혜영', '차장', '생산기술팀', '1978-09-08', 'default', '2012-01-31', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('199100003', 'SFY001KOR', '오나라', '대리', '영업팀', '1966-04-08', 'default', '1991-02-27', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('200000002', 'SFY001KOR', '한지아', '사원', '영업팀', '1981-12-27', 'default', '2000-07-25', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201600003', 'SFY001KOR', '임창기', '사원', '생산기술팀', '1971-05-18', 'default', '2016-06-27', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('199900002', 'SFY001KOR', '정민상', '사원', '경영지원팀', '1980-07-03', 'default', '1999-11-06', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('200800002', 'SFY001KOR', '김지우', '대리', '경영지원팀', '1983-10-31', 'default', '2008-12-15', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201500004', 'SFY001KOR', '최형찬', '대리', '개발팀', '1985-01-31', 'default', '2015-03-26', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('200600005', 'SFY001KOR', '최창기', '대리', '개발팀', '1983-12-02', 'default', '2006-11-18', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202300004', 'SFY001KOR', '임창기', '사원', '경영지원팀', '2004-07-24', 'default', '2023-01-25', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201800006', 'SFY001KOR', '임부진', '부장', '개발팀', '1999-03-23', 'default', '2018-04-19', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202000005', 'SFY001KOR', '정종건', '사원', '개발팀', '1989-11-01', 'default', '2020-06-20', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201200003', 'SFY001KOR', '조성철', '대리', '생산기술팀', '1979-01-17', 'default', '2012-08-23', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('200500004', 'SFY001KOR', '조재인', '사원', '영업팀', '1980-11-18', 'default', '2005-11-05', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201200004', 'SFY001KOR', '최부진', '대리', '영업팀', '1991-11-02', 'default', '2012-10-01', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201900003', 'SFY001KOR', '한건', '대리', '생산기술팀', '1979-01-09', 'default', '2019-03-25', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('199800002', 'SFY001KOR', '임명박', '과장', '영업팀', '1965-10-02', 'default', '1998-10-06', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('200300003', 'SFY001KOR', '최재용', '사원', '개발팀', '1965-08-26', 'default', '2003-06-11', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202300005', 'SFY001KOR', '김우재', '대리', '품질관리팀', '2000-02-27', 'default', '2023-06-19', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('199400001', 'SFY001KOR', '김창기', '대리', '개발팀', '1968-01-22', 'default', '1994-07-30', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('200700005', 'SFY001KOR', '오현진', '사원', '개발팀', '1985-07-27', 'default', '2007-07-18', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202100007', 'SFY001KOR', '한하은', '대리', '생산기술팀', '1988-06-10', 'default', '2021-01-20', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201000003', 'SFY001KOR', '윤서연', '사원', '개발팀', '1990-04-13', 'default', '2010-11-15', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202300006', 'SFY001KOR', '한민상', '사원', '경영지원팀', '1982-02-11', 'default', '2023-09-27', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202300007', 'SFY001KOR', '정서연', '사원', '개발팀', '1987-12-31', 'default', '2023-07-09', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201100006', 'SFY001KOR', '정형찬', '대리', '경영지원팀', '1963-05-18', 'default', '2011-02-14', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201300003', 'SFY001KOR', '조혜영', '사원', '생산기술팀', '1972-01-23', 'default', '2013-07-06', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201300004', 'SFY001KOR', '한승호', '사원', '생산기술팀', '1982-03-21', 'default', '2013-03-06', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202200004', 'SFY001KOR', '장태원', '차장', '경영지원팀', '1960-07-23', 'default', '2022-05-29', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202300008', 'SFY001KOR', '오재용', '사원', '개발팀', '2001-12-13', 'default', '2023-09-03', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202100008', 'SFY001KOR', '이지아', '대리', '개발팀', '1966-02-14', 'default', '2021-07-06', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('198900003', 'SFY001KOR', '한민상', '사원', '생산기술팀', '1959-06-20', 'default', '1989-10-25', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201500005', 'SFY001KOR', '박재명', '사원', '영업팀', '1972-05-03', 'default', '2015-12-10', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201600004', 'SFY001KOR', '장미숙', '대리', '개발팀', '1985-02-26', 'default', '2016-04-27', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202000006', 'SFY001KOR', '윤재용', '사원', '품질관리팀', '1962-05-12', 'default', '2020-08-23', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202100009', 'SFY001KOR', '한지우', '사원', '생산기술팀', '1999-05-06', 'default', '2021-03-13', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202200005', 'SFY001KOR', '박명박', '대리', '경영지원팀', '1999-07-18', 'default', '2022-05-31', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201800007', 'SFY001KOR', '김석열', '과장', '경영지원팀', '1985-04-12', 'default', '2018-09-13', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201900004', 'SFY001KOR', '정미숙', '사원', '개발팀', '1973-10-29', 'default', '2019-09-28', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201100007', 'SFY001KOR', '한영철', '사원', '품질관리팀', '1982-10-15', 'default', '2011-01-24', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('200600006', 'SFY001KOR', '이지우', '대리', '영업팀', '1974-12-04', 'default', '2006-07-05', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201700004', 'SFY001KOR', '정유진', '사원', '경영지원팀', '1990-09-23', 'default', '2017-04-13', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202300009', 'SFY001KOR', '박유진', '사원', '개발팀', '2004-08-06', 'default', '2023-06-09', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('199300001', 'SFY001KOR', '한근혜', '사원', '품질관리팀', '1975-04-04', 'default', '1993-10-26', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201200005', 'SFY001KOR', '오지우', '차장', '개발팀', '1976-05-09', 'default', '2012-06-10', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202200006', 'SFY001KOR', '조진명', '대리', '개발팀', '2002-10-10', 'default', '2022-08-07', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202300010', 'SFY001KOR', '정나라', '사원', '개발팀', '1991-06-15', 'default', '2023-07-05', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202100010', 'SFY001KOR', '이준호', '대리', '생산기술팀', '1995-10-20', 'default', '2021-02-19', '2024-04-07', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('200500005', 'SFY001KOR', '임서연', '사원', '영업팀', '1981-03-17', 'default', '2005-11-09', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202200007', 'SFY001KOR', '윤건', '사원', '영업팀', '1983-10-11', 'default', '2022-12-19', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202200008', 'SFY001KOR', '임하은', '과장', '경영지원팀', '1985-01-25', 'default', '2022-03-12', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201800008', 'SFY001KOR', '최재용', '대리', '품질관리팀', '1964-10-18', 'default', '2018-12-02', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201700005', 'SFY001KOR', '정건', '대리', '경영지원팀', '1975-09-17', 'default', '2017-05-28', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201300005', 'SFY001KOR', '오서연', '대리', '개발팀', '1989-02-15', 'default', '2013-02-01', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201100008', 'SFY001KOR', '김명박', '과장', '개발팀', '1969-09-19', 'default', '2011-03-04', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201700006', 'SFY001KOR', '한태원', '대리', '영업팀', '1998-07-16', 'default', '2017-12-10', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201200006', 'SFY001KOR', '박지훈', '차장', '품질관리팀', '1985-01-21', 'default', '2012-03-24', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202100011', 'SFY001KOR', '김민철', '사원', '품질관리팀', '2002-03-21', 'default', '2021-03-15', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('199400002', 'SFY001KOR', '오승호', '대리', '경영지원팀', '1974-03-07', 'default', '1994-10-11', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('200800003', 'SFY001KOR', '박민수', '사원', '생산기술팀', '1976-03-27', 'default', '2008-09-06', '2023-11-29', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202300011', 'SFY001KOR', '정현비', '차장', '품질관리팀', '1993-02-21', 'default', '2023-08-28', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202200009', 'SFY001KOR', '오재인', '사원', '영업팀', '1999-12-02', 'default', '2022-12-01', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202100012', 'SFY001KOR', '김수민', '사원', '개발팀', '1982-06-09', 'default', '2021-03-30', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201800009', 'SFY001KOR', '윤태원', '과장', '영업팀', '1992-12-31', 'default', '2018-11-30', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202100013', 'SFY001KOR', '임태원', '사원', '품질관리팀', '2003-05-26', 'default', '2021-07-19', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('200500006', 'SFY001KOR', '장유진', '사원', '품질관리팀', '1974-12-09', 'default', '2005-06-06', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('199600001', 'SFY001KOR', '김지아', '사원', '경영지원팀', '1959-11-18', 'default', '1996-12-15', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('200500007', 'SFY001KOR', '조부진', '사원', '생산기술팀', '1961-04-13', 'default', '2005-09-17', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('200900005', 'SFY001KOR', '박재인', '사원', '개발팀', '1961-03-02', 'default', '2009-07-15', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201400003', 'SFY001KOR', '장영마', '사원', '경영지원팀', '1987-02-12', 'default', '2014-04-26', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202300012', 'SFY001KOR', '오정민', '대리', '개발팀', '1994-09-03', 'default', '2023-10-07', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('201600005', 'SFY001KOR', '한민상', '사원', '개발팀', '1974-07-13', 'default', '2016-05-25', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202200010', 'SFY001KOR', '최하은', '차장', '개발팀', '2004-06-20', 'default', '2022-07-01', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('202200011', 'SFY001KOR', '정재명', '대리', '생산기술팀', '1972-09-11', 'default', '2022-10-21', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
;

INSERT INTO app_user (USER_IS_LOCKED, USER_PIN, CREATED_AT, UPDATED_AT, USER_ID, EMPLOYEE_NUM, USER_STAT, USER_ROLE, USER_PWD) VALUES
                                                                                                                                   (false, '$2a$10$l1FAI5aM0A9ocigVa7L34eU6IvWO/nu0Vln5/ZawHkCdDtcVXBZvm', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, '202100001', 'INIT', 'ROLE_MANAGER', '$2a$10$pT.Qqif0f1VbpAT2OSCvCe88qQqYWX2//YXi.T722zEKj/N9gr3Ue'),
                                                                                                                                   (false, '$2a$10$FXTUrU8805Vnxtzv3cH2aO82F4h3nyAQe/C/NWTRBdxD2beWkusLS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, '199500001', 'INIT', 'ROLE_WORKER', '$2a$10$8lOqas3sQFbrpesrOM80oO14xs4finryPmroZMnbu/mKdh7VZhZ9q');

INSERT INTO check_list_template(check_list_template_context)
values ('LOTO에 대해 작업자 교육이 이루어졌는가?'),
       ('해당 기계 혹은 설비의 전원을 차단하였는가?'),
       ('LOTO가 해당 기계·설비에 부착되어 있는가?'),
       ('LOTO 사용 시 해당 기계·설비 권한이 있는 작업자를 지정·공유하였는가?'),
       ('LOTO 표지가 잘 부착되어 있는가?'),
       ('작업자는 기계·설비의 전원을 차단하는 등 LOTO 작업절차를 준수하고 있는가?'),
       ('작업자 눈에 잘 띄는 위치에 표지판을 단단히 고정하여 경고하였는가?'),
       ('LOTO를 해제하기 전 다른 작업자들이 안전하게 위치하고 있는지 확인하였는가?'),
       ('모든 작업자들에게 LOTO 제거를 공지하였는가?');

INSERT INTO task_template(task_template_type, task_template_precaution)
values ('FIX', '수리중입니다.'),
       ('FIX', '메인 장치 수리중입니다.'),
       ('FIX', '보조 장치 수리중입니다.'),
       ('REPAIR', '점검중입니다.'),
       ('REPAIR', '정기 점검중입니다.'),
       ('REPAIR', '임시 점검중입니다.'),
       ('CLEAN', '청소중입니다.'),
       ('CLEAN', '정기 청소중입니다.'),
       ('CLEAN', '임시 청소중입니다.'),
       ('ETC', '');

INSERT INTO locker(locker_battery, locker_is_locked, locker_uid, company_code, created_at, updated_at, locker_encryption_key)
values (100, false, 'MURBMjRHMzE=', 'SFY001KOR', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Y3R5pIjVCzW1uz0XJFs123=='),
       (10, true, 'MURBMjRHMzI=', 'SFY001KOR', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'abXQxLvYzK2QwFDyrux345=='),
       (80, false, 'MURBMjRHMzM=', 'SFY001KOR', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'CdT54ZOmKJGH5Gd1Mkp678=='),
       (98, false, 'MURBMjRHMzQ=', 'SFY001KOR', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,'ZvcM41Zr5Nj2FBSaQlm901=='),
       (29, false, 'MURBMjRHMzU=', 'SFY001KOR', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'XjR55ZmnmPQ2Jb3Vg4t234=='),
       (80, false, 'MURBMjRHMzY=', 'SFY001KOR', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Hng2Q3XPY7aV0FzQwRp567=='),
       (88, true, 'MURBMjRHMzc=', 'SFY001KOR', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'BvcM91Zr3Dj7FBSqPno890=='),
       (91, false, 'MURBMjRHMzg=', 'SFY001KOR', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Ope34ZmnmC5TUb9Vh6t123=='),
       (37, false, 'MURBMjRHMzk=', 'SFY001KOR', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Mjn8D5XmZTg6Rb0Ph3q456==');


insert into facility(created_at, updated_at, facility_company_code, facility_name, facility_address, facility_layout, facility_thum)
values (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'SFY001KOR', '1공장', '광주광역시 광산구 하남산단6번로 107', '', ''),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'SFY001KOR', '2공장', '광주광역시 광산구 하남산단6번로 107', '', ''),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'SFY001KOR', '3공장', '광주광역시 광산구 하남산단6번로 107', '', ''),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'SFY001KOR', '4공장', '광주광역시 광산구 하남산단6번로 107', '', ''),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'SFY001KOR', '5공장', '광주광역시 광산구 하남산단6번로 107', '', ''),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'SFY001KOR', '6공장', '광주광역시 광산구 하남산단6번로 107', '', '');


INSERT INTO machine_subcategory (machine_subcategory)
VALUES
    ('선반'),
    ('수치제어 선반'),
    ('보통선반'),
    ('자동선반'),
    ('수직선반'),
    ('터릿선반'),
    ('탁상선반'),
    ('나사절삭선반'),
    ('수제제어 수평선반'),
    ('밀링기'),
    ('수치제어 밀링머신'),
    ('베드형 밀링머신'),
    ('플래노밀러'),
    ('머시닝센터'),
    ('드릴링기'),
    ('래디얼 드릴링머신'),
    ('탁상 드릴링머신'),
    ('보링기'),
    ('수치제어 보링머신'),
    ('수직형 보링머신'),
    ('지그 보링머신'),
    ('연삭기'),
    ('수치제어 연삭기'),
    ('평면연삭기'),
    ('기어가공기'),
    ('수치제어 기어호빙머신'),
    ('베벨기어가공기'),
    ('특수가공기'),
    ('수치제어 와이어컷 방전기'),
    ('레이저 가공기'),
    ('기타 절삭기계'),
    ('파워 프레스'),
    ('크랭크 프레스'),
    ('고속 프레스'),
    ('딥 드로잉 프레스'),
    ('하이드로포밍 프레스'),
    ('수치제어 절단기'),
    ('파이프 밴딩머신'),
    ('단조 프레스'),
    ('볼트/너트 포밍머신'),
    ('와이어 드로잉머신'),
    ('드로우 벤치'),
    ('스프링포밍 머신');

INSERT INTO MACHINE
    (MACHINE_LATITUDE, MACHINE_LONGITUDE, CREATED_AT, FACILITY_ID, MACHINE_ID, MACHINE_INTRODUCTION_DATE, MACHINE_SUBCATEGORY, UPDATED_AT, MACHINE_LOCKER_TYPE, MACHINE_NAME, MACHINE_NUMBER, MACHINE_COMPANY, MACHINE_THUM)
VALUES
    (0, 0, '2024-05-07 14:37:42.935150', 1, 1, '2021-02-01 09:00:00.000000', null, '2024-05-07 14:37:42.935150', 'NO', 'machineName', 'machineCode', null, 'machineThum');
