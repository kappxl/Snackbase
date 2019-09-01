

CREATE TABLE `ingredients` (
  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
  `name` TEXT NOT NULL,
  `cals` INTEGER NOT NULL,
  `carbs` NUMERIC NOT NULL,
  `protein` NUMERIC NOT NULL,
  `fat` NUMERIC NOT NULL,
  `grams` INTEGER DEFAULT NULL
) ;



INSERT INTO `ingredients` (`id`, `name`, `cals`, `carbs`, `protein`, `fat`, `grams`) VALUES
(1, 'Ei (M)', 155, '1.10', '13.00', '11.00', 53),
(2, 'Speckwürfel (125 gr)', 224, '1.00', '21.00', '15.00', 125),
(3, 'Salami', 305, '1.00', '19.00', '25.00', 17),
(4, 'Käse', 354, '0.50', '25.00', '28.00', 32),
(5, 'Choco Fresh', 583, '39.30', '7.30', '34.70', 21),
(6, 'Magerquark (L-Frei)', 70, '4.30', '12.50', '0.30', 250),
(7, 'Joghurt (3.8 %, L-Frei)', 63, '3.70', '3.60', '3.80', 500),
(8, 'Müsli (Schoko-Krokant)', 444, '64.00', '8.50', '16.00', 65),
(9, 'Kidney Bohnen', 111, '12.60', '8.40', '1.00', 255),
(10, 'Basmati Reis (trocken)', 351, '77.00', '9.00', '0.50', 65),
(14, 'Walnüsse', 712, '3.70', '15.50', '69.10', 35),
(15, 'Kürbiskerne', 593, '3.60', '34.40', '47.70', 35),
(16, 'Haferflocken', 372, '58.70', '13.50', '7.00', 85),
(17, 'Banane', 96, '22.00', '1.00', '0.20', 120),
(18, 'Whey Protein (Bodylab)', 406, '6.70', '78.50', '7.00', 45),
(19, 'Mozzarella Sticks', 307, '27.70', '14.40', '14.90', 250),
(20, 'Kaiser Gemüse', 40, '4.00', '3.00', '0.70', 150),
(21, 'Brustfleisch', 110, '0.00', '23.00', '2.00', 400),
(22, 'Toast (Vollkorn, klein)', 266, '42.00', '8.80', '5.50', 25),
(23, 'Kakao Pulver', 380, '79.80', '4.50', '3.10', 20),
(24, 'Nudeln', 350, '70.50', '12.50', '1.20', 80),
(25, 'Passierte Tomaten', 33, '5.60', '1.70', '0.10', 500),
(26, 'Zwiebel', 28, '4.90', '1.20', '0.30', 85),
(27, 'Milch (L-Frei)', 47, '4.90', '3.40', '1.50', 200),
(28, 'Paprika (rot)', 37, '6.40', '1.30', '0.50', 105),
(29, 'Hackfleisch (gemischt)', 234, '0.00', '18.00', '18.00', 500),
(31, 'Gurke', 12, '1.80', '0.60', '0.20', 400),
(32, 'Zucchini', 20, '2.20', '1.60', '0.40', 280),
(33, 'Hamburger Sauce', 405, '17.00', '1.10', '37.00', 15),
(34, 'Mango', 62, '12.50', '0.60', '0.40', 200),
(35, 'Speckwürfel - light', 135, '1.00', '26.00', '3.00', 75),
(36, 'Hüttenkäse - Milbona light', 69, '3.10', '12.40', '0.80', 300),
(37, 'Mozzarella light', 157, '1.00', '19.00', '8.50', 125),
(38, 'Dürüm', 303, '50.10', '7.50', '7.50', 100),
(39, 'Mais', 80, '10.80', '2.90', '1.90', 140),
(40, 'Schnittlauch', 27, '1.60', '3.60', '0.60', 5),
(41, 'Süßkartoffel', 86, '20.00', '1.60', '0.10', 380),
(42, 'Thunfisch (in Sonnenblumenöl)', 191, '0.00', '25.20', '10.00', 135),
(43, 'Sweet Chilli Sauce', 158, '39.00', '0.60', '0.60', 30),
(44, 'Salat', 36, '6.40', '1.50', '0.30', 30),
(45, 'Aubergine', 25, '4.00', '1.00', '0.20', 400);



CREATE TABLE `meals` (
  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
  `name` TEXT NOT NULL,
  `breakfast` tinyint(4) NOT NULL,
  `lunch` tinyint(4) NOT NULL,
  `dinner` tinyint(4) NOT NULL
);

INSERT INTO `meals` (`id`, `name`, `breakfast`, `lunch`, `dinner`) VALUES
(1, 'Rührei mit Speck', 1, 0, 1),
(2, 'Rührei', 1, 0, 1),
(4, 'Porridge (mit Banane)', 1, 0, 1),
(5, 'Quarkshake - Kakao/Nuss', 1, 0, 1),
(6, 'Chilli Con Carne mit Nudeln', 0, 1, 1),
(7, 'Joghurt mit Müsli', 1, 0, 1),
(8, '1/2 Joghurt mit Müsli', 1, 0, 1),
(9, 'Whey Shake (Bodylab)', 1, 1, 1),
(10, 'Hähnchendürüm', 0, 1, 1),
(11, 'Mango Salat', 0, 1, 1),
(12, 'Thunfischdürüm', 0, 0, 1),
(13, 'Auberginenhack', 0, 1, 1);



CREATE TABLE `recipes` (
  `meal_id` INTEGER NOT NULL,
  `ingredient_id` INTEGER NOT NULL,
  `amount` NUMERIC NOT NULL,
  
  CONSTRAINT `pk_recipes` PRIMARY KEY (`meal_id`, `ingredient_id`),
  CONSTRAINT `UQ_recipes_food_id_ingredient_id` UNIQUE (`meal_id`, `ingredient_id`)
);


INSERT INTO `recipes` (`meal_id`, `ingredient_id`, `amount`) VALUES
(1, 1, '4.00'),
(1, 2, '1.00'),
(2, 1, '4.00'),
(4, 16, '1.00'),
(4, 17, '1.00'),
(5, 6, '1.00'),
(5, 14, '1.00'),
(5, 23, '1.00'),
(5, 27, '1.00'),
(6, 9, '0.50'),
(6, 24, '1.00'),
(6, 25, '0.50'),
(6, 26, '1.00'),
(6, 28, '0.50'),
(6, 29, '0.50'),
(7, 7, '1.00'),
(7, 8, '1.00'),
(8, 7, '0.50'),
(8, 8, '1.00'),
(9, 18, '1.00'),
(10, 21, '0.50'),
(10, 28, '0.50'),
(10, 33, '2.00'),
(10, 37, '0.50'),
(11, 1, '1.00'),
(11, 14, '1.00'),
(11, 16, '0.50'),
(11, 34, '0.50'),
(11, 36, '1.00'),
(11, 39, '0.50'),
(11, 40, '2.00'),
(12, 38, '1.00'),
(12, 42, '1.00'),
(12, 43, '1.00'),
(12, 44, '1.00'),
(13, 26, '1.00'),
(13, 29, '1.00'),
(13, 45, '0.50');


