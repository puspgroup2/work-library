CREATE TABLE TimeReports (
reportID Integer NOT NULL,
userName varChar(20) NOT NULL,
totalMinutes Integer,
signature varChar(20),
week Integer NOT NULL,
PRIMARY KEY(reportID),
FOREIGN KEY(userName) REFERENCES Users(userName)
ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY(signature) REFERENCES Users(userName)
ON UPDATE CASCADE ON DELETE SET NULL
);