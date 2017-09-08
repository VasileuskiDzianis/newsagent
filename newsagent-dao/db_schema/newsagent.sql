DROP TABLE IF EXISTS news;
CREATE TABLE news (
	id INT NOT NULL AUTO_INCREMENT,
	newsDate DATE NOT NULL,
	title VARCHAR(100) NOT NULL,
	brief VARCHAR(500) NOT NULL,
	content VARCHAR(2048) NOT NULL,
	status VARCHAR(10) NOT NULL,
	PRIMARY KEY (id)
)ENGINE=InnoDB CHARSET=utf8;

LOCK TABLES news WRITE;

INSERT INTO news VALUES
	('1', '2017-09-01', 
	'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor.', 
	
	'The European languages are members of the same family. Their separate existence is a myth. For science, music, sport, etc, 
Europe uses the same vocabulary. The languages only differ in their grammar, their pronunciation and their most common words. 
Everyone realizes why a new common language would be desirable: one could refuse to pay expensive translators. To achieve this, 
it would be necessary to have uniform grammar.',

'One morning, when Gregor Samsa woke from troubled dreams, he found himself transformed in his bed into a horrible vermin. 
He lay on his armour-like back, and if he lifted his head a little he could see his brown belly, slightly domed and divided 
by arches into stiff sections. The bedding was hardly able to cover it and seemed ready to slide off any moment. His many legs, 
pitifully thin compared with the size of the rest of him, waved about helplessly as he looked. Whats happened to me he thought. 
It wasnt a dream. His room, a proper human room although a little too small, lay peacefully between its four familiar walls. 
A collection of textile samples lay spread out on the table - Samsa was a travelling salesman - and above it there hung a picture 
that he had recently cut out of an illustrated magazine and housed in a nice, gilded frame. It showed a lady fitted out with a fur 
hat and fur boa who sat upright, raising a heavy fur muff that covered the whole of her lower arm towards the viewer. Gregor then 
turned to look out the window at the dull weather. Drops of rain could be heard hitting the pane, which made him feel quite sad. 
How about if I sleep a little bit longer and forget all this nonsense", he thought, but that was something he was unable to do 
because he was used to sleeping on his right, and in his present state couldnt get into that position. However hard he threw 
himself onto his right, he always rolled back to where he was. He must have tried it a hundred times, shut his eyes so that he 
wouldnt have to look at the floundering legs, and only stopped when he began to feel a mild, dull pain there that he had never 
felt before.',
'ACTUAL');
INSERT INTO news VALUES
	('2', '2017-09-02', 
	'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m', 
	
	'The European languages are members of the same family. Their separate existence is a myth. For science, music, sport, etc, 
Europe uses the same vocabulary. The languages only differ in their grammar, their pronunciation and their most common words. 
Everyone realizes why a new common language would be desirable: one could refuse to pay expensive translators. To achieve this, 
it would be necessary to have uniform grammar, pronunciation and more common words.',

'One morning, when Gregor Samsa woke from troubled dreams, he found himself transformed in his bed into a horrible vermin. 
He lay on his armour-like back, and if he lifted his head a little he could see his brown belly, slightly domed and divided 
by arches into stiff sections. The bedding was hardly able to cover it and seemed ready to slide off any moment. His many legs, 
pitifully thin compared with the size of the rest of him, waved about helplessly as he looked. Whats happened to me he thought. 
It wasnt a dream. His room, a proper human room although a little too small, lay peacefully between its four familiar walls. 
A collection of textile samples lay spread out on the table - Samsa was a travelling salesman - and above it there hung a picture 
that he had recently cut out of an illustrated magazine and housed in a nice, gilded frame. It showed a lady fitted out with a fur 
hat and fur boa who sat upright, raising a heavy fur muff that covered the whole of her lower arm towards the viewer. Gregor then 
turned to look out the window at the dull weather. Drops of rain could be heard hitting the pane, which made him feel quite sad. 
How about if I sleep a little bit longer and forget all this nonsense", he thought, but that was something he was unable to do 
because he was used to sleeping on his right, and in his present state couldnt get into that position. However hard he threw 
himself onto his right, he always rolled back to where he was. He must have tried it a hundred times, shut his eyes so that he 
wouldnt have to look at the floundering legs, and only stopped when he began to feel a mild, dull pain there that he had never 
felt before. "Oh, God", he thought, "what a strenuous career it is that Ive chosen!',
'ACTUAL');

INSERT INTO news VALUES
	('3', '2017-09-03', 'Short title 3', 'Short brief 3', 'Short content 3', 'ACTUAL');
INSERT INTO news VALUES
	('4', '2017-09-04', 'Archived news title', 'Archived news brief', 'Archived news content', 'ARCHIVED');
INSERT INTO news VALUES
	('5', '2017-09-05', 'Short title 5', 'Short brief 5', 'Short content 5', 'ACTUAL');
INSERT INTO news VALUES
	('6', '2017-09-06', 'Short title 6', 'Short brief 6', 'Short content 6', 'ACTUAL');
INSERT INTO news VALUES
	('7', '2017-09-07', 'Short title 7', 'Short brief 7', 'Short content 7', 'ACTUAL');
INSERT INTO news VALUES
	('8', '2017-09-08', 'Short title 8', 'Short brief 8', 'Short content 8', 'ACTUAL');

UNLOCK TABLES;