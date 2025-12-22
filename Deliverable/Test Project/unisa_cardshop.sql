SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

--
-- Database: `unisa_cardshop`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `categoria`
--

CREATE TABLE `categoria` (
  `id` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `categoria`
--

INSERT INTO `categoria` (`id`, `nome`) VALUES
(1, 'YuGiOh'),
(2, 'Pokemon'),
(3, 'Magic'),
(4, 'Sportive'),
(5, 'Accessori');

-- --------------------------------------------------------

--
-- Struttura della tabella `prodotto`
--

CREATE TABLE `prodotto` (
  `id` int(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `descrizione` text NOT NULL,
  `prezzo` double NOT NULL,
  `quantita` int(11) NOT NULL,
  `categoria_id` int(11) NOT NULL,
  `is_disponibile` tinyint(1) NOT NULL DEFAULT 1,
  `specifiche` text DEFAULT NULL,
  `foto` longblob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `prodotto` (Esempio con prodotti esauriti)
--

INSERT INTO `prodotto` (`id`, `nome`, `descrizione`, `prezzo`, `quantita`, `categoria_id`, `is_disponibile`, `specifiche`) VALUES
-- Prodotto esaurito per test (ID 1 - YuGiOh)
(1, 'Drago Bianco Occhi Blu', 'Rara carta leggendaria di Yu-Gi-Oh.', 49.99, 0, 1, 1, 'Condizione: Near Mint'), 
(2, 'Mago Nero', 'Il fedele servitore di Yugi.', 25.50, 10, 1, 1, 'Edizione Limitata'),
-- Prodotto esaurito per test (ID 6 - Pokemon)
(6, 'Charizard GX', 'Carta Pokemon ultra rara.', 120.00, 0, 2, 1, 'Olografica'),
(7, 'Pikachu Illustrator', 'Copia promozionale rarissima.', 500.00, 1, 2, 1, 'Gradata PSA 10'),
(11, 'Black Lotus', 'La carta più iconica di Magic.', 1500.00, 2, 3, 1, 'Alpha Edition'),
(20, 'Bustine Protettive UltraPro', 'Pacco da 100 bustine trasparenti.', 5.99, 50, 5, 1, 'Acid Free'),
(25, 'LeBron James Rookie Card', 'Carta sportiva da collezione NBA.', 300.00, 3, 4, 1, 'Upper Deck 2003');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `prodotto`
--
ALTER TABLE `prodotto`
  ADD PRIMARY KEY (`id`),
  ADD KEY `categoria_id` (`categoria_id`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `categoria`
--
ALTER TABLE `categoria`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT per la tabella `prodotto`
--
ALTER TABLE `prodotto`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- Limiti per la tabella `prodotto`
--
ALTER TABLE `prodotto`
  ADD CONSTRAINT `prodotto_ibfk_1` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`id`) ON DELETE CASCADE;

COMMIT;