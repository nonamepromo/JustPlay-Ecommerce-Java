-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Mag 15, 2021 alle 12:13
-- Versione del server: 10.4.18-MariaDB
-- Versione PHP: 7.3.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `justplay`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `videogiochi`
--

CREATE TABLE `videogiochi` (
  `id` int(11) NOT NULL,
  `titolo` varchar(32) NOT NULL,
  `piattaforma` varchar(200) NOT NULL,
  `annoDiUscita` int(11) NOT NULL,
  `descrizione` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `videogiochi`
--

INSERT INTO `videogiochi` (`id`, `titolo`, `piattaforma`, `annoDiUscita`, `descrizione`) VALUES
(1, 'The Witcher 3', 'Pc', 2015, 'The Witcher 3 Wild Hunt è il terzo episodio della serie creata da CD Projekt: la nuova avventura di Geralt propone situazioni mature e una trama dalle atmosfere oscure.'),
(2, 'Gta V', 'Xbox One', 2013, 'Grand Theft Auto 5 è ambientato nell\'enorme Los Santos; il gioco ci metterà nei panni di tre diversi protagonisti, ognuno con una propria determinata personalità ed un\'esistenza con cui fare i conti.'),
(3, 'Bloodborne', 'Ps4', 2015, 'Bloodborne è ambientato in una città dimenticata (Yharnam), in questa amena località viene prododotta una preziosa sostanza capace di curare numerose malattie.'),
(4, 'Sekiro', 'Ps4', 2019, 'In Sekiro: Shadows Die Twice si vestiranno i panni di un \"lupo senza un braccio\", un guerriero sfregiato e caduto in disgrazia, salvato a un passo dalla morte.');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `videogiochi`
--
ALTER TABLE `videogiochi`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
