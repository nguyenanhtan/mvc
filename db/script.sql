CREATE TABLE IF NOT EXISTS SESSIONS
(
	id INT NOT NULL AUTO_INCREMENT,
	time TIMESTAMP,
	solution VARCHAR(256),
	depot INT,
	num_vehicle INT,
	capacity INT,
	PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS POSITIONS
(
	id INT NOT NULL AUTO_INCREMENT,
	LatLng varchar(256),
	duration INT,
	PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS REQUEST
(
	id INT NOT NULL AUTO_INCREMENT,
	id_pickup INT,
	id_deliver INT,
	id_session INT,
	weight INT,
	Ep varchar(10),
	Lp varchar(10),
	Ed varchar(10),
	Ld varchar(10),
	PRIMARY KEY (id),
	FOREIGN KEY (id_pickup) REFERENCES POSITIONS(id),
	FOREIGN KEY (id_deliver) REFERENCES POSITIONS(id),
	FOREIGN KEY (id_session) REFERENCES SESSIONS(id)
);


CREATE TABLE IF NOT EXISTS ROUTCOST
(
	id_position_1 INT NOT NULL,
	id_position_2 INT NOT NULL,
	distance FLOAT,
	duration FLOAT,
	PRIMARY KEY(id_position_1,id_position_2),
	FOREIGN KEY (id_position_1) REFERENCES POSITIONS(id),
	FOREIGN KEY (id_position_2) REFERENCES POSITIONS(id)
);

