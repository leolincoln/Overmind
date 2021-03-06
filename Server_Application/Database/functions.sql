--function to get the room schudle given a person's id
--used as such: SELECT getRoomSchedule('userid123');
CREATE OR REPLACE FUNCTION getRoomSchedule(rid varchar) RETURNS SETOF schedulesubu AS '
DECLARE
	r schedulesubu;
BEGIN
	CREATE TABLE tmp(user_id VARCHAR(100) , time_start TIMESTAMP, time_end TIMESTAMP);
	INSERT INTO tmp
		SELECT reservation.user_id, reservation.start_time, reservation.end_time
		FROM reservation
		WHERE reservation.room_id = rid
		ORDER BY reservation.start_time
	;
	FOR r in SELECT * FROM tmp
	LOOP
		RETURN NEXT r;
	END LOOP;
	DROP TABLE tmp CASCADE;
END;
' LANGUAGE plpgsql;
--function: given a particular roomid and a date, we want to get all the reservations for that date
CREATE OR REPLACE FUNCTION getRoomScheduleDate(rid varchar, ndate TIMESTAMP) RETURNS SETOF schedulesubu AS '
DECLARE
	r schedulesubu;
BEGIN
	CREATE TABLE tmp(user_id VARCHAR(100) , time_start TIMESTAMP, time_end TIMESTAMP);
	INSERT INTO tmp
		SELECT reservation.user_id, reservation.start_time, reservation.end_time
		FROM reservation
		WHERE reservation.room_id = rid
			AND DATE(reservation.start_time) = DATE(ndate)
	;
	FOR r in SELECT * FROM tmp
	LOOP
		RETURN NEXT r;
	END LOOP;
	DROP TABLE tmp CASCADE;
END;
' LANGUAGE plpgsql;

--function: given a particular roomid and a start and end time for reservation, return all reservations for specified time
CREATE OR REPLACE FUNCTION getRoomScheduleTime(rid varchar, nstart TIMESTAMP, nend TIMESTAMP) RETURNS SETOF schedulesubu AS '
DECLARE
	r schedulesubu;
BEGIN
	CREATE TABLE tmp(user_id VARCHAR(100) , time_start TIMESTAMP, time_end TIMESTAMP);
	INSERT INTO tmp
		SELECT reservation.user_id, reservation.start_time, reservation.end_time
		FROM reservation
		WHERE reservation.room_id = rid
			AND ((reservation.start_time >= nstart
			AND reservation.end_time <= nstart)
			OR (
			reservation.start_time >= nend
			AND reservation.end_time <= nend
			))
	;
	FOR r in SELECT * FROM tmp
	LOOP
		RETURN NEXT r;
	END LOOP;
	DROP TABLE tmp CASCADE;
END;

' LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION getUserSchedule(uid varchar) RETURNS SETOF schedulesubr AS '
DECLARE
	r schedulesubr;
BEGIN
	CREATE TABLE tmp(room_id VARCHAR(100) , time_start TIMESTAMP, time_end TIMESTAMP);
	INSERT INTO tmp
		SELECT reservation.room_id, reservation.start_time, reservation.end_time
		FROM reservation
		WHERE reservation.user_id = uid
		ORDER BY reservation.start_time
	;
	FOR r in SELECT * FROM tmp
	LOOP
		RETURN NEXT r;
	END LOOP;
	DROP TABLE tmp CASCADE;
END;
' LANGUAGE plpgsql;

