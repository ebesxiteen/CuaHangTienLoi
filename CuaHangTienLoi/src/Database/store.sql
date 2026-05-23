-- MySQL/MariaDB script for CuaHangTienLoi
-- Database: store

SET SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO';
START TRANSACTION;
SET time_zone = '+00:00';

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

CREATE DATABASE IF NOT EXISTS `store`
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci;

USE `store`;

CREATE TABLE IF NOT EXISTS `cthoadon` (
  `mahd` varchar(255) NOT NULL,
  `masp` varchar(255) NOT NULL,
  `soluong` int NOT NULL,
  `dongia` int NOT NULL,
  `thanhtien` int NOT NULL,
  KEY `mahd` (`mahd`),
  KEY `masp` (`masp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `ctphieunhap` (
  `mapn` varchar(255) NOT NULL,
  `masp` varchar(255) NOT NULL,
  `soluong` int NOT NULL,
  `dongia` int NOT NULL,
  `thanhtien` int NOT NULL,
  KEY `mapn` (`mapn`),
  KEY `masp` (`masp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `danhmuc` (
  `maloai` varchar(255) NOT NULL,
  `tenloai` varchar(255) NOT NULL,
  `img` varchar(255) NOT NULL,
  PRIMARY KEY (`maloai`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `hoadon` (
  `mahd` varchar(255) NOT NULL,
  `makh` varchar(255) NOT NULL,
  `manv` varchar(255) NOT NULL,
  `ngaytao` varchar(255) NOT NULL,
  `tongtien` int NOT NULL,
  PRIMARY KEY (`mahd`),
  KEY `makh` (`makh`),
  KEY `manv` (`manv`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `khachhang` (
  `makh` varchar(255) NOT NULL,
  `ten` varchar(255) NOT NULL,
  `sdt` varchar(15) NOT NULL,
  `email` varchar(255) NOT NULL,
  PRIMARY KEY (`makh`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `khachhang` (`makh`, `ten`, `sdt`, `email`) VALUES
('kh001', 'Thao Nguyen', '0989879098', 'Thao@gmail.com'),
('kh002', 'Khang Minh', '0987876876', 'khan22222g3333@gmail.com'),
('khang345', 'mKhang', '0987886987', 'khang2222@gmail.com'),
('khanghaha', 'khanghihih', '0987987876', 'khangha@gmail.com'),
('Tan5', 'Tan3', '0989876789', 'khang34@gmail.com'),
('Tan879', 'TanHaha', '0987987987', 'Tan34@gmail.com'),
('Thanh44444', 'Thanh', '0987987654', 'thanh@gmail.com'),
('Tuan384', 'Tuan', '0987987098', 'Tuan@gmail.com');

CREATE TABLE IF NOT EXISTS `login` (
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(30) NOT NULL,
  `status` int NOT NULL,
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `login` (`username`, `password`, `status`) VALUES
('nv001', 'nv001', 0),
('nv002', 'nv002', 1);

CREATE TABLE IF NOT EXISTS `nhacungcap` (
  `mancc` varchar(255) NOT NULL,
  `tenncc` varchar(255) NOT NULL,
  `tenndd` varchar(255) NOT NULL,
  `sdt` varchar(20) NOT NULL,
  `diachi` varchar(255) NOT NULL,
  PRIMARY KEY (`mancc`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `nhanvien` (
  `manv` varchar(255) NOT NULL,
  `ho` varchar(255) NOT NULL,
  `ten` varchar(255) NOT NULL,
  `gioitinh` varchar(255) NOT NULL,
  `sdt` varchar(15) NOT NULL,
  `ngaysinh` varchar(255) NOT NULL,
  `chucvu` varchar(255) NOT NULL,
  `luong` int NOT NULL,
  PRIMARY KEY (`manv`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `nhanvien` (`manv`, `ho`, `ten`, `gioitinh`, `sdt`, `ngaysinh`, `chucvu`, `luong`) VALUES
('nv001', 'Van', 'D', 'Nam', '0987654345', '17/09/1999', 'Quan ly', 11000000),
('nv002', 'Van', 'A', 'Khac', '0897876876', '27/07/1997', 'Nhan vien thu ngan', 7000000);

CREATE TABLE IF NOT EXISTS `phieunhap` (
  `mapn` varchar(255) NOT NULL,
  `mancc` varchar(255) NOT NULL,
  `manv` varchar(255) NOT NULL,
  `ngaytao` varchar(255) NOT NULL,
  `tongtien` int NOT NULL,
  PRIMARY KEY (`mapn`),
  KEY `mancc` (`mancc`),
  KEY `manv` (`manv`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `sanpham` (
  `masp` varchar(255) NOT NULL,
  `tensp` varchar(255) NOT NULL,
  `maloaisp` varchar(255) NOT NULL,
  `soluong` int NOT NULL,
  `dongia` int NOT NULL,
  `img` varchar(255) NOT NULL,
  PRIMARY KEY (`masp`),
  KEY `maloaisp` (`maloaisp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

ALTER TABLE `cthoadon`
  ADD CONSTRAINT `cthd_hd` FOREIGN KEY (`mahd`) REFERENCES `hoadon` (`mahd`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `sp_cthd` FOREIGN KEY (`masp`) REFERENCES `sanpham` (`masp`) ON DELETE NO ACTION ON UPDATE CASCADE;

ALTER TABLE `ctphieunhap`
  ADD CONSTRAINT `ctpn_pn` FOREIGN KEY (`mapn`) REFERENCES `phieunhap` (`mapn`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `sp_ctpn` FOREIGN KEY (`masp`) REFERENCES `sanpham` (`masp`) ON DELETE NO ACTION ON UPDATE CASCADE;

ALTER TABLE `hoadon`
  ADD CONSTRAINT `hoadon_ibfk_1` FOREIGN KEY (`makh`) REFERENCES `khachhang` (`makh`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `hoadon_ibfk_2` FOREIGN KEY (`manv`) REFERENCES `nhanvien` (`manv`) ON DELETE NO ACTION ON UPDATE CASCADE;

ALTER TABLE `login`
  ADD CONSTRAINT `login_ibfk_1` FOREIGN KEY (`username`) REFERENCES `nhanvien` (`manv`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `phieunhap`
  ADD CONSTRAINT `phieunhap_ibfk_1` FOREIGN KEY (`mancc`) REFERENCES `nhacungcap` (`mancc`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `phieunhap_ibfk_2` FOREIGN KEY (`manv`) REFERENCES `nhanvien` (`manv`) ON DELETE NO ACTION ON UPDATE CASCADE;

ALTER TABLE `sanpham`
  ADD CONSTRAINT `sanpham_ibfk_1` FOREIGN KEY (`maloaisp`) REFERENCES `danhmuc` (`maloai`) ON UPDATE CASCADE;

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
