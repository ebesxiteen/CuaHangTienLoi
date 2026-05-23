-- MySQL/MariaDB script for CuaHangTienLoi
-- Re-runnable version: it drops and recreates the database each time.

SET SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO';
SET time_zone = '+00:00';

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

DROP DATABASE IF EXISTS `store`;
CREATE DATABASE `store`
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci;

USE `store`;

CREATE TABLE `danhmuc` (
  `maloai` varchar(255) NOT NULL,
  `tenloai` varchar(255) NOT NULL,
  `img` varchar(255) NOT NULL,
  PRIMARY KEY (`maloai`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `nhacungcap` (
  `mancc` varchar(255) NOT NULL,
  `tenncc` varchar(255) NOT NULL,
  `tenndd` varchar(255) NOT NULL,
  `sdt` varchar(20) NOT NULL,
  `diachi` varchar(255) NOT NULL,
  PRIMARY KEY (`mancc`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `nhanvien` (
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

CREATE TABLE `khachhang` (
  `makh` varchar(255) NOT NULL,
  `ten` varchar(255) NOT NULL,
  `sdt` varchar(15) NOT NULL,
  `email` varchar(255) NOT NULL,
  PRIMARY KEY (`makh`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `login` (
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(30) NOT NULL,
  `status` int NOT NULL,
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `phieunhap` (
  `mapn` varchar(255) NOT NULL,
  `mancc` varchar(255) NOT NULL,
  `manv` varchar(255) NOT NULL,
  `ngaytao` varchar(255) NOT NULL,
  `tongtien` int NOT NULL,
  PRIMARY KEY (`mapn`),
  KEY `mancc` (`mancc`),
  KEY `manv` (`manv`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `sanpham` (
  `masp` varchar(255) NOT NULL,
  `tensp` varchar(255) NOT NULL,
  `maloaisp` varchar(255) NOT NULL,
  `soluong` int NOT NULL,
  `dongia` int NOT NULL,
  `img` varchar(255) NOT NULL,
  PRIMARY KEY (`masp`),
  KEY `maloaisp` (`maloaisp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `ctphieunhap` (
  `mapn` varchar(255) NOT NULL,
  `masp` varchar(255) NOT NULL,
  `soluong` int NOT NULL,
  `dongia` int NOT NULL,
  `thanhtien` int NOT NULL,
  PRIMARY KEY (`mapn`, `masp`),
  KEY `masp` (`masp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `hoadon` (
  `mahd` varchar(255) NOT NULL,
  `makh` varchar(255) NOT NULL,
  `manv` varchar(255) NOT NULL,
  `ngaytao` varchar(255) NOT NULL,
  `tongtien` int NOT NULL,
  PRIMARY KEY (`mahd`),
  KEY `makh` (`makh`),
  KEY `manv` (`manv`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `cthoadon` (
  `mahd` varchar(255) NOT NULL,
  `masp` varchar(255) NOT NULL,
  `soluong` int NOT NULL,
  `dongia` int NOT NULL,
  `thanhtien` int NOT NULL,
  PRIMARY KEY (`mahd`, `masp`),
  KEY `masp` (`masp`)
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

INSERT INTO `nhanvien` (`manv`, `ho`, `ten`, `gioitinh`, `sdt`, `ngaysinh`, `chucvu`, `luong`) VALUES
('nv001', 'Van', 'D', 'Nam', '0987654345', '17/09/1999', 'Quan ly', 11000000),
('nv002', 'Van', 'A', 'Khac', '0897876876', '27/07/1997', 'Nhan vien thu ngan', 7000000);

INSERT INTO `login` (`username`, `password`, `status`) VALUES
('nv001', 'nv001', 0),
('nv002', 'nv002', 1);

INSERT INTO `danhmuc` (`maloai`, `tenloai`, `img`) VALUES
('dm001', 'Đồ ăn', 'img/doan.png'),
('dm002', 'Nước uống', 'img/nuocuong.png'),
('dm003', 'Văn phòng phẩm', 'img/hoctap.png');

INSERT INTO `nhacungcap` (`mancc`, `tenncc`, `tenndd`, `sdt`, `diachi`) VALUES
('ncc001', 'Công ty TNHH A', 'Nguyễn A', '0901111222', 'Hà Nội'),
('ncc002', 'Công ty TNHH B', 'Trần B', '0903333444', 'Hồ Chí Minh');

INSERT INTO `sanpham` (`masp`, `tensp`, `maloaisp`, `soluong`, `dongia`, `img`) VALUES
('sp001', 'Bánh mì', 'dm001', 100, 10000, 'img/banhmi.png'),
('sp002', 'Phở gà', 'dm001', 50, 45000, 'img/pho.png'),
('sp003', 'Coca Cola', 'dm002', 200, 15000, 'img/cocacola.png'),
('sp004', 'Sổ tay A5', 'dm003', 150, 12000, 'img/sotay.png');

INSERT INTO `phieunhap` (`mapn`, `mancc`, `manv`, `ngaytao`, `tongtien`) VALUES
('pn001', 'ncc001', 'nv002', '2026-05-01', 500000),
('pn002', 'ncc002', 'nv001', '2026-05-05', 300000);

INSERT INTO `ctphieunhap` (`mapn`, `masp`, `soluong`, `dongia`, `thanhtien`) VALUES
('pn001', 'sp001', 100, 4000, 400000),
('pn001', 'sp003', 20, 5000, 100000),
('pn002', 'sp002', 50, 6000, 300000);

INSERT INTO `hoadon` (`mahd`, `makh`, `manv`, `ngaytao`, `tongtien`) VALUES
('hd001', 'kh001', 'nv002', '2026-05-10', 70000),
('hd002', 'kh002', 'nv001', '2026-05-12', 30000),
('hd003', 'khang345', 'nv002', '2026-05-20', 90000);

INSERT INTO `cthoadon` (`mahd`, `masp`, `soluong`, `dongia`, `thanhtien`) VALUES
('hd001', 'sp001', 2, 10000, 20000),
('hd001', 'sp003', 2, 25000, 50000),
('hd002', 'sp004', 2, 12000, 24000),
('hd003', 'sp002', 1, 45000, 45000),
('hd003', 'sp001', 1, 10000, 10000);

ALTER TABLE `ctphieunhap`
  ADD CONSTRAINT `ctpn_pn` FOREIGN KEY (`mapn`) REFERENCES `phieunhap` (`mapn`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `sp_ctpn` FOREIGN KEY (`masp`) REFERENCES `sanpham` (`masp`) ON DELETE NO ACTION ON UPDATE CASCADE;

ALTER TABLE `cthoadon`
  ADD CONSTRAINT `cthd_hd` FOREIGN KEY (`mahd`) REFERENCES `hoadon` (`mahd`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `sp_cthd` FOREIGN KEY (`masp`) REFERENCES `sanpham` (`masp`) ON DELETE NO ACTION ON UPDATE CASCADE;

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

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;