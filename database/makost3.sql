-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 03 Des 2023 pada 15.06
-- Versi server: 10.4.28-MariaDB
-- Versi PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `makost3`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `admin`
--

CREATE TABLE `admin` (
  `username` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `nama_admin` varchar(40) NOT NULL,
  `alamat` varchar(70) NOT NULL,
  `email` varchar(30) NOT NULL,
  `no_tlp` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data untuk tabel `admin`
--

INSERT INTO `admin` (`username`, `password`, `nama_admin`, `alamat`, `email`, `no_tlp`) VALUES
('admin', 'admin1234', 'admin', 'jember	', 'admin@gmail.com', 82244866126);

-- --------------------------------------------------------

--
-- Struktur dari tabel `kamar`
--

CREATE TABLE `kamar` (
  `no_kamar` int(11) NOT NULL,
  `harga` int(11) NOT NULL,
  `kapasitas` varchar(11) NOT NULL,
  `lantai` int(11) NOT NULL,
  `fasilitas` varchar(100) NOT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'Tersedia'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data untuk tabel `kamar`
--

INSERT INTO `kamar` (`no_kamar`, `harga`, `kapasitas`, `lantai`, `fasilitas`, `status`) VALUES
(1, 500000, '1 Orang', 1, 'kasur, lemari, meja, kursi, kamar mandi dalam', 'Terisi'),
(2, 500000, '1 Orang', 1, 'kasur, lemari, meja, kursi, kamar mandi dalam', 'Tersedia'),
(3, 500000, '1 Orang', 1, 'kasur, lemari, meja, kursi, kamar mandi dalam', 'Terisi'),
(4, 500000, '1 Orang', 1, 'kasur, lemari, meja, kursi, kamar mandi dalam', 'Terisi'),
(5, 500000, '1 Orang', 1, 'kasur, lemari, meja, kursi, kamar mandi dalam', 'Tersedia');

-- --------------------------------------------------------

--
-- Struktur dari tabel `pembayaran`
--

CREATE TABLE `pembayaran` (
  `id_pembayaran` int(11) NOT NULL,
  `NIK` varchar(30) NOT NULL,
  `no_kamar` int(11) NOT NULL,
  `harga` int(11) NOT NULL,
  `bulan` varchar(20) NOT NULL,
  `tahun` varchar(10) NOT NULL,
  `tgl_pembayaran` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data untuk tabel `pembayaran`
--

INSERT INTO `pembayaran` (`id_pembayaran`, `NIK`, `no_kamar`, `harga`, `bulan`, `tahun`, `tgl_pembayaran`) VALUES
(57, '3202080504910003', 3, 500000, 'Oktober', '2023', '2023-12-03 03:45:45'),
(58, '7312042510720002', 1, 500000, 'Oktober', '2023', '2023-12-03 03:46:00'),
(59, '3216011203670005', 4, 500000, 'Oktober', '2023', '2023-12-03 03:46:48'),
(60, '3216011203670005', 4, 500000, 'November', '2023', '2023-12-03 03:48:14');

-- --------------------------------------------------------

--
-- Struktur dari tabel `penghuni`
--

CREATE TABLE `penghuni` (
  `NIK` varchar(30) NOT NULL,
  `Nama` varchar(40) NOT NULL,
  `Alamat` varchar(60) NOT NULL,
  `No_Handphone` char(15) NOT NULL,
  `Tanggal_Lahir` date NOT NULL,
  `Registrasi` date NOT NULL,
  `no_kamar` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data untuk tabel `penghuni`
--

INSERT INTO `penghuni` (`NIK`, `Nama`, `Alamat`, `No_Handphone`, `Tanggal_Lahir`, `Registrasi`, `no_kamar`) VALUES
('3202080504910003', 'Laura', 'Banyuwangi', '083833092724', '2005-07-25', '2023-12-03', 3),
('3216011203670005', 'Melvina', 'Bogor', '085216709554', '2005-04-25', '2023-12-03', 4),
('7312042510720002', 'Ita', 'Bondowoso', '082336938797', '2006-06-13', '2023-12-03', 1);

--
-- Trigger `penghuni`
--
DELIMITER $$
CREATE TRIGGER `hapus_tagihan` AFTER DELETE ON `penghuni` FOR EACH ROW BEGIN
DELETE FROM tagihan
WHERE NIK = OLD.NIK;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `pengisian_kamar` AFTER INSERT ON `penghuni` FOR EACH ROW BEGIN
UPDATE kamar
SET status = "Terisi"
WHERE no_kamar = NEW.no_kamar;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `perubahan_status` AFTER DELETE ON `penghuni` FOR EACH ROW BEGIN
UPDATE kamar
SET status = "Tersedia"
WHERE no_kamar = OLD.no_kamar;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `tagihan_penghuni` AFTER INSERT ON `penghuni` FOR EACH ROW BEGIN
	INSERT INTO tagihan (no_kamar, NIK, Nama)
    VALUES (NEW.no_kamar, NEW.NIK, NEW.Nama);
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `update_status1` AFTER UPDATE ON `penghuni` FOR EACH ROW BEGIN
UPDATE kamar
SET status = "Tersedia"
WHERE no_kamar = OLD.no_kamar;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `update_status2` AFTER UPDATE ON `penghuni` FOR EACH ROW BEGIN
UPDATE kamar
SET status = "Terisi"
WHERE no_kamar = NEW.no_kamar;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `update_tagihan` AFTER UPDATE ON `penghuni` FOR EACH ROW BEGIN
UPDATE tagihan
SET 
no_kamar = NEW.no_kamar
WHERE NIK = OLD.NIK;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Struktur dari tabel `tagihan`
--

CREATE TABLE `tagihan` (
  `id_tagihan` int(11) NOT NULL,
  `no_kamar` int(11) NOT NULL,
  `NIK` varchar(30) NOT NULL,
  `Nama` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data untuk tabel `tagihan`
--

INSERT INTO `tagihan` (`id_tagihan`, `no_kamar`, `NIK`, `Nama`) VALUES
(89, 3, '3202080504910003', 'Laura'),
(90, 1, '7312042510720002', 'Ita'),
(91, 4, '3216011203670005', 'Melvina');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`username`);

--
-- Indeks untuk tabel `kamar`
--
ALTER TABLE `kamar`
  ADD PRIMARY KEY (`no_kamar`);

--
-- Indeks untuk tabel `pembayaran`
--
ALTER TABLE `pembayaran`
  ADD PRIMARY KEY (`id_pembayaran`),
  ADD KEY `no_kamar` (`no_kamar`),
  ADD KEY `NIK` (`NIK`);

--
-- Indeks untuk tabel `penghuni`
--
ALTER TABLE `penghuni`
  ADD PRIMARY KEY (`NIK`),
  ADD UNIQUE KEY `No_Kamar` (`no_kamar`);

--
-- Indeks untuk tabel `tagihan`
--
ALTER TABLE `tagihan`
  ADD PRIMARY KEY (`id_tagihan`),
  ADD KEY `no_kamar` (`no_kamar`),
  ADD KEY `NIK` (`NIK`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `pembayaran`
--
ALTER TABLE `pembayaran`
  MODIFY `id_pembayaran` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=61;

--
-- AUTO_INCREMENT untuk tabel `tagihan`
--
ALTER TABLE `tagihan`
  MODIFY `id_tagihan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=92;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `pembayaran`
--
ALTER TABLE `pembayaran`
  ADD CONSTRAINT `pembayaran_ibfk_1` FOREIGN KEY (`no_kamar`) REFERENCES `kamar` (`no_kamar`),
  ADD CONSTRAINT `pembayaran_ibfk_2` FOREIGN KEY (`NIK`) REFERENCES `penghuni` (`NIK`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `penghuni`
--
ALTER TABLE `penghuni`
  ADD CONSTRAINT `penghuni_ibfk_1` FOREIGN KEY (`no_kamar`) REFERENCES `kamar` (`no_kamar`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `tagihan`
--
ALTER TABLE `tagihan`
  ADD CONSTRAINT `tagihan_ibfk_1` FOREIGN KEY (`no_kamar`) REFERENCES `kamar` (`no_kamar`),
  ADD CONSTRAINT `tagihan_ibfk_2` FOREIGN KEY (`NIK`) REFERENCES `penghuni` (`NIK`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
