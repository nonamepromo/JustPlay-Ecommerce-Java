-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Giu 24, 2021 alle 13:47
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
-- Struttura della tabella `conversazioni`
--

CREATE TABLE `conversazioni` (
  `fk_utente1` int(11) NOT NULL,
  `fk_utente2` int(11) NOT NULL,
  `chat` longtext NOT NULL,
  `data` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `conversazioni`
--

INSERT INTO `conversazioni` (`fk_utente1`, `fk_utente2`, `chat`, `data`) VALUES
(1, 2, 'Ciao; Ciao', '2020-01-01'),
(1, 3, 'Ciaone;Ciaone', '2020-01-02'),
(1, 4, 'Ciao!;Salve;Come Stai?;Tutto bene', '2020-01-03');

-- --------------------------------------------------------

--
-- Struttura della tabella `messaggi`
--

CREATE TABLE `messaggi` (
  `id` int(11) NOT NULL,
  `fk_conversazione` int(11) NOT NULL,
  `contenuto` varchar(300) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `ruoli`
--

CREATE TABLE `ruoli` (
  `id_ruolo` int(10) NOT NULL,
  `nome` varchar(20) NOT NULL,
  `descrizione` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `ruoli`
--

INSERT INTO `ruoli` (`id_ruolo`, `nome`, `descrizione`) VALUES
(1, 'amministratore', NULL),
(2, 'presidentecad', NULL),
(3, 'segreteriadidattica', NULL);

-- --------------------------------------------------------

--
-- Struttura della tabella `ruoli_utenti`
--

CREATE TABLE `ruoli_utenti` (
  `id_utente` int(10) NOT NULL,
  `id_ruolo` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `ruoli_utenti`
--

INSERT INTO `ruoli_utenti` (`id_utente`, `id_ruolo`) VALUES
(1, 1),
(2, 2),
(3, 3);

-- --------------------------------------------------------

--
-- Struttura della tabella `utenti`
--

CREATE TABLE `utenti` (
  `id_utente` int(10) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `cognome` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `data_nascita` date NOT NULL,
  `tipologia_utente` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `utenti`
--

INSERT INTO `utenti` (`id_utente`, `username`, `password`, `nome`, `cognome`, `email`, `data_nascita`, `tipologia_utente`) VALUES
(1, 'admin', '$2a$10$e59rGaFvpijWXLh03j0aZOzBYQNrIRIjlB8sGwLvBL35fecblsW1m', 'Admin', 'Admin', 'admin.disim@univaq.it', '1971-01-01', 0),
(2, 'muccini', '$2y$10$tKGXgR7VcvHeIihJVFvIs.y7q5ooGEwoVMX7izrYWbqYPmkSAmPIO', 'Henry', 'Muccini', 'henry.muccini@univaq.it', '1971-01-01', 1),
(3, 'segreteriadidattica', '$2a$10$qX/mU9UXjwIZZtkb2mhaWORPbdofkm4JJLUH4zl8ZmjfUOwk4BI2C', 'Segreteria', 'Didattica', 'amleto.disalle@univaq.it', '1971-01-01', 0),
(4, 'amleto', '$2a$10$PJHE5nSsjHCdHLVgwuGKiuakibmdTOpUV51ksCezIXSS5YAlS9y76', 'Amleto', 'Di Salle', 'amleto.disalle@univaq.it', '1971-01-01', 1),
(5, 'studente', '$2a$10$hX6cJVcl4sT6Btg01XdXFeTPf3BujwEbI.VOs6aFOwQZCh46rMONe', 'studente', 'studente', 'studente.studente@univaq.it', '1971-01-01', 2),
(6, 'rossi', '$2y$10$Zmf6f7qUb4mQd2Fw.13HOuTkwyodpsYLodlFXPU7DIXbE1lR/3wEy', 'Fabrizio', 'Rossi', 'fabrizio.rossi@univaq.it', '1971-01-01', 1),
(7, 'gino', '$2a$10$zee6NDvwLHFVck/lOPzbpuGImcT.e502GK5IgbJSnpizRnMWIwxre', 'gino', 'gino', 'gino@gino.gino', '2013-05-14', 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `videogiochi`
--

CREATE TABLE `videogiochi` (
  `id` int(11) NOT NULL,
  `titolo` varchar(32) NOT NULL,
  `piattaforma` varchar(200) NOT NULL,
  `annoDiUscita` int(11) NOT NULL,
  `descrizione` varchar(185) NOT NULL,
  `ps4Url` varchar(4096) DEFAULT NULL,
  `xboxUrl` varchar(4096) DEFAULT NULL,
  `pcUrl` varchar(4096) DEFAULT NULL,
  `imageUrl` varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `videogiochi`
--

INSERT INTO `videogiochi` (`id`, `titolo`, `piattaforma`, `annoDiUscita`, `descrizione`, `ps4Url`, `xboxUrl`, `pcUrl`, `imageUrl`) VALUES
(0, 'Gears 5', '', 2019, 'Da una delle saghe più acclamate dei videogiochi, Gears è più grande che mai, con cinque modalità mozzafiato e la campagna più entusiasmante di sempre.', NULL, 'https://www.amazon.it/Gears-War-5-Xbox-One/dp/B07DMJMZ86/ref=sr_1_1?__mk_it_IT=%C3%85M%C3%85%C5%BD%C3%95%C3%91&dchild=1&keywords=gears+5&qid=1622811603&sr=8-1', NULL, 'https://www.gears5.com/static/e7317a2bb749f0510dd21561fdfdd03a/gears5.jpg'),
(1, 'The Witcher 3: Wild Hunt', 'Pc', 2015, 'The Witcher 3 Wild Hunt è il terzo episodio della serie creata da CD Projekt: la nuova avventura di Geralt propone situazioni mature e una trama dalle atmosfere oscure.', 'https://www.amazon.it/Witcher-III-Game-Year-PlayStation/dp/B01KJZYXW6/ref=sr_1_8?__mk_it_IT=%C3%85M%C3%85%C5%BD%C3%95%C3%91&dchild=1&keywords=the+witcher&qid=1622805601&sr=8-8;https://www.unieuro.it/online/Giochi-Playstation-4/The-Witcher-3-Wild-Hunt---Game-of-the-Year-Edition-PlayStation-4-pidINS112117;https://www.mediaworld.it/product/p-724383/witcher-iii-wild-hunt-game-year-ps4', 'https://www.amazon.it/Witcher-III-Game-Year-Xbox/dp/B01KJZYWVS/ref=sr_1_1?__mk_it_IT=%C3%85M%C3%85%C5%BD%C3%95%C3%91&crid=50P6OKV5TH0A&dchild=1&keywords=the+witcher+3+xbox+one&qid=1622809641&sprefix=the+witcher+3%2Caps%2C221&sr=8-1', 'https://www.amazon.it/Witcher-Game-Year-DVD-Edizione/dp/B01JYW2EY4/ref=sr_1_1?__mk_it_IT=%C3%85M%C3%85%C5%BD%C3%95%C3%91&dchild=1&keywords=the+witcher+3+pc&qid=1622809668&sr=8-1', 'https://image.api.playstation.com/vulcan/img/rnd/202009/2913/TQKAd8U6hnIFQIIcz6qnFh8C.png'),
(2, 'Grand Theft Auto V', 'Xbox One', 2013, 'Grand Theft Auto 5 è ambientato a Los Santos e ci metterà nei panni di tre diversi protagonisti, ognuno con una propria determinata personalità ed un\'esistenza con cui fare i conti.', 'https://www.amazon.it/Grand-Theft-Auto-GTA-PlayStation/dp/B00KWBFV7O/ref=sr_1_2?__mk_it_IT=%C3%85M%C3%85%C5%BD%C3%95%C3%91&crid=3DWNBQEIOYCIU&dchild=1&keywords=gta+v+ps4&qid=1622811819&sprefix=gta+v%2Caps%2C415&sr=8-2', 'https://www.amazon.it/Grand-Theft-Auto-GTA-Xbox/dp/B00KXX4UWI/ref=sr_1_8?__mk_it_IT=%C3%85M%C3%85%C5%BD%C3%95%C3%91&dchild=1&keywords=gta+v&qid=1622811791&sr=8-8;https://www.unieuro.it/online/Giochi-Xbox-One/GTA-V---Xbox-One-pidTWOSWX10075;https://www.mediaworld.it/product/p-110310/gta-v-premium-edition-xbox-one', NULL, 'https://www.staynerd.com/wp-content/uploads/gtaV-1.jpg'),
(3, 'Bloodborne', 'Ps4', 2015, 'Bloodborne è ambientato in una città dimenticata (Yharnam), in questa amena località viene prododotta una preziosa sostanza capace di curare numerose malattie.', 'https://www.amazon.it/Bloodborne-Ps-Hits-Classics-PlayStation/dp/B07FJKT4G3/ref=sr_1_1?__mk_it_IT=%C3%85M%C3%85%C5%BD%C3%95%C3%91&crid=2KD94BAGMH2IH&dchild=1&keywords=bloodborne&qid=1622810636&sprefix=bloodborne%2Caps%2C201&sr=8-1;https://www.unieuro.it/online/Giochi-Playstation-4/PS4-Hits-Bloodborne-pidSON9436775', NULL, NULL, 'https://game-experience.it/wp-content/uploads/2017/12/bloodborne-1200x900.jpg'),
(4, 'Sekiro: Shadows Die Twice', 'Ps4', 2019, 'In Sekiro: Shadows Die Twice si vestiranno i panni di un \"lupo senza un braccio\", un guerriero sfregiato e caduto in disgrazia, salvato a un passo dalla morte.', 'https://www.amazon.it/SEKIRO-Shadows-Die-Twice-PlayStation/dp/B07DPJPWSK/ref=sr_1_1?__mk_it_IT=%C3%85M%C3%85%C5%BD%C3%95%C3%91&dchild=1&keywords=sekiro+ps4&qid=1622811881&sr=8-1;https://www.unieuro.it/online/Giochi-Playstation-4/Sekiro-shadows-die-twice---Playstation-4-pidATV88292IT', 'https://www.amazon.it/SEKIRO-SHADOWS-TWICE-xbox_one-Edizione/dp/B07DNQD2GQ/ref=sr_1_1?__mk_it_IT=%C3%85M%C3%85%C5%BD%C3%95%C3%91&crid=FBRPFI6R6NUN&dchild=1&keywords=sekiro+xbox+one&qid=1622811865&sprefix=sekiro+x%2Caps%2C192&sr=8-1', 'https://www.amazon.it/Sekiro-Shadows-Twice-Edizione-Francia/dp/B07FG615HS/ref=sr_1_1?__mk_it_IT=%C3%85M%C3%85%C5%BD%C3%95%C3%91&dchild=1&keywords=sekiro+pc&qid=1622811853&sr=8-1;https://www.unieuro.it/online/Giochi-computer/Sekiro-shadows-die-twice---Computer-pidATV33566IT', 'https://techprincess.it/wp-content/uploads/2019/03/sekiro-shadows-die-twice.jpg');

-- --------------------------------------------------------

--
-- Struttura della tabella `videogiochi_desiderati`
--

CREATE TABLE `videogiochi_desiderati` (
  `fk_utente` int(11) NOT NULL,
  `fk_videogioco` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `videogiochi_desiderati`
--

INSERT INTO `videogiochi_desiderati` (`fk_utente`, `fk_videogioco`) VALUES
(1, 0),
(1, 2),
(1, 3),
(1, 4);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `messaggi`
--
ALTER TABLE `messaggi`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `utenti`
--
ALTER TABLE `utenti`
  ADD PRIMARY KEY (`id_utente`);

--
-- Indici per le tabelle `videogiochi`
--
ALTER TABLE `videogiochi`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `videogiochi_desiderati`
--
ALTER TABLE `videogiochi_desiderati`
  ADD PRIMARY KEY (`fk_utente`,`fk_videogioco`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `utenti`
--
ALTER TABLE `utenti`
  MODIFY `id_utente` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
