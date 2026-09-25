-- ===================================================================
-- Smart Campus System - Initial Seed Data (sample_data.sql)
-- ====================================================================

-- 1. Insert Administrator (Full powers)
INSERT INTO users (id, username, password, email, role, full_name, phone, status)
VALUES ('usr_admin_001', 'admin', 'admin123', 'admin@kqu.ac.ug', 'ADMIN', 'Prof. Arthur Ssenkumba', '+256 700 112 233', 'ACTIVE');

-- 2. Insert the 8 Core Faculties
INSERT INTO faculties (id, name, code, dean) VALUES
('fac_health', 'Faculty of Health Sciences / Medicine', 'FHS', 'Prof. Margaret Kasule, MD, PhD'),
('fac_eng', 'Faculty of Engineering, Technology & Design', 'FETD', 'Prof. Eng. Ronald Bwambale, PhD, REng'),
('fac_comp', 'Faculty of Computing & Information Science', 'FCIS', 'Dr. Agnes Nakitto, PhD (Computer Science)'),
('fac_agric', 'Faculty of Agriculture & Animal Sciences', 'FAAS', 'Prof. Jackson Mwesigwa, PhD (Agric)'),
('fac_bus', 'Faculty of Business, Economics & Management', 'FBEM', 'Prof. Robert Kigozi, PhD, FCPA'),
('fac_law', 'Faculty of Law', 'FOL', 'Prof. Justice Sylvia Tamale, LL.D'),
('fac_edu', 'Faculty of Education & Humanities', 'FEH', 'Dr. Livingstone Ssewanyana, PhD (Edu)'),
('fac_art', 'School of Art and Industrial Design', 'SAID', 'Assoc. Prof. George Kyeyune, PhD (Art)');

-- 3. Insert Courses under Faculties
INSERT INTO courses (id, faculty_id, name, code, award_type, duration_years, total_credits) VALUES
-- Health
('crs_mbchb', 'fac_health', 'Bachelor of Medicine and Bachelor of Surgery (MBChB)', 'MBChB', 'Bachelor', 5, 250),
('crs_bds', 'fac_health', 'Bachelor of Dental Surgery (BDS)', 'BDS', 'Bachelor', 5, 240),
('crs_pha', 'fac_health', 'Bachelor of Pharmacy (PHA)', 'PHA', 'Bachelor', 4, 200),
('crs_nurs', 'fac_health', 'Bachelor of Nursing Science (Direct)', 'BNS', 'Bachelor', 4, 190),
('crs_pubhealth', 'fac_health', 'Bachelor of Science in Public Health', 'BSPH', 'Bachelor', 3, 150),

-- Engineering
('crs_civ', 'fac_eng', 'Bachelor of Civil Engineering', 'CIV', 'Bachelor', 4, 195),
('crs_elec', 'fac_eng', 'Bachelor of Electrical Engineering', 'ELE', 'Bachelor', 4, 195),
('crs_mech', 'fac_eng', 'Bachelor of Mechanical Engineering', 'MEC', 'Bachelor', 4, 195),
('crs_bio', 'fac_eng', 'Bachelor of Science in Biosystems Engineering', 'BIO', 'Bachelor', 4, 190),
('crs_arch', 'fac_eng', 'Bachelor of Architecture', 'ARC', 'Bachelor', 5, 240),
('crs_survey', 'fac_eng', 'Bachelor of Science in Land Surveying and Geomatics', 'SUR', 'Bachelor', 4, 185),

-- Computing
('crs_cs', 'fac_comp', 'Bachelor of Science in Computer Science', 'BCS', 'Bachelor', 3, 150),
('crs_se', 'fac_comp', 'Bachelor of Science in Software Engineering', 'BSSE', 'Bachelor', 4, 190),
('crs_it', 'fac_comp', 'Bachelor of Information Technology (IT)', 'BIT', 'Bachelor', 3, 145),
('crs_bbc', 'fac_comp', 'Bachelor of Business Computing', 'BBC', 'Bachelor', 3, 140),
('crs_cs_edu', 'fac_comp', 'Bachelor of Computer Science with Education', 'BCSE', 'Bachelor', 3, 145),
('crs_blis', 'fac_comp', 'Bachelor of Library and Information Science', 'BLIS', 'Bachelor', 3, 135),
('crs_rim', 'fac_comp', 'Bachelor of Records and Information Management', 'BRIM', 'Bachelor', 3, 135),
('crs_oim', 'fac_comp', 'Bachelor of Office and Information Management', 'BOIM', 'Bachelor', 3, 135),
('crs_dlis', 'fac_comp', 'Diploma in Library & Information Science', 'DLIS', 'Diploma', 2, 90),
('crs_dcsit', 'fac_comp', 'Diploma in Computer Science & Information Technology', 'DCIT', 'Diploma', 2, 90),

-- Agriculture
('crs_agr', 'fac_agric', 'Bachelor of Science in Agriculture', 'BSA', 'Bachelor', 4, 180),
('crs_food_agri', 'fac_agric', 'Bachelor of Science in Food Bioscience & Agribusiness', 'FBA', 'Bachelor', 4, 180),
('crs_food_tech', 'fac_agric', 'Bachelor of Science in Food Science and Technology', 'FST', 'Bachelor', 4, 180),
('crs_animal_prod', 'fac_agric', 'Bachelor of Animal Production and Management', 'BAPM', 'Bachelor', 3, 140),
('crs_hort', 'fac_agric', 'Bachelor of Science in Horticulture', 'BSH', 'Bachelor', 3, 140),
('crs_dip_animal', 'fac_agric', 'Diploma in Animal Production & Management', 'DAPM', 'Diploma', 2, 90),
('crs_dip_crop', 'fac_agric', 'Diploma in Crop Production & Management', 'DCPM', 'Diploma', 2, 90),

-- Business
('crs_bba', 'fac_bus', 'Bachelor of Business Administration (BBA)', 'BBA', 'Bachelor', 3, 140),
('crs_bcom', 'fac_bus', 'Bachelor of Commerce (BCom)', 'BCOM', 'Bachelor', 3, 140),
('crs_econ', 'fac_bus', 'Bachelor of Arts in Economics', 'BAE', 'Bachelor', 3, 135),
('crs_acc', 'fac_bus', 'Bachelor of Science in Accounting and Finance', 'BAF', 'Bachelor', 3, 140),
('crs_ent', 'fac_bus', 'Bachelor of Entrepreneurship & Small Business Management', 'BESM', 'Bachelor', 3, 135),
('crs_proc', 'fac_bus', 'Bachelor of Procurement and Supply Chain Management', 'BPSM', 'Bachelor', 3, 140),
('crs_tour', 'fac_bus', 'Bachelor of Tourism and Hospitality Management', 'BTHM', 'Bachelor', 3, 135),
('crs_dip_acc', 'fac_bus', 'Diploma in Accounting and Finance', 'DAF', 'Diploma', 2, 90),
('crs_dip_bida', 'fac_bus', 'Diploma in Business Intelligence and Data Analytics', 'DBIDA', 'Diploma', 2, 90),
('crs_dip_micro', 'fac_bus', 'Diploma in Microfinance (Distance Learning)', 'DMF', 'Diploma', 2, 85),

-- Law
('crs_llb', 'fac_law', 'Bachelor of Laws (LL.B)', 'LLB', 'Bachelor', 5, 245),
('crs_dip_law', 'fac_law', 'Diploma in Law', 'DLAW', 'Diploma', 3, 110),

-- Education
('crs_edu_bio', 'fac_edu', 'Bachelor of Science with Education (Biological)', 'BED-BIO', 'Bachelor', 3, 145),
('crs_edu_phy', 'fac_edu', 'Bachelor of Science with Education (Physical)', 'BED-PHY', 'Bachelor', 3, 145),
('crs_edu_mathecon', 'fac_edu', 'Bachelor of Science with Education (Mathematics & Economics)', 'BED-MEC', 'Bachelor', 3, 145),
('crs_edu_chem', 'fac_edu', 'Bachelor of Science in Education (Chemistry)', 'BED-CHM', 'Bachelor', 3, 140),
('crs_edu_geo', 'fac_edu', 'Bachelor of Science in Education (Geography)', 'BED-GEO', 'Bachelor', 3, 140),
('crs_edu_physics', 'fac_edu', 'Bachelor of Science in Education (Physics)', 'BED-PHS', 'Bachelor', 3, 140),
('crs_edu_math', 'fac_edu', 'Bachelor of Science in Education (Mathematics)', 'BED-MTH', 'Bachelor', 3, 140),
('crs_edu_sport', 'fac_edu', 'Bachelor of Science Education (Sport and Exercise Science)', 'BED-SPT', 'Bachelor', 3, 140),
('crs_edu_early', 'fac_edu', 'Bachelor of Early Childhood and Pre-Primary Education', 'BECE', 'Bachelor', 3, 135),

-- Art & Design
('crs_art_fine', 'fac_art', 'Bachelor of Fine Art', 'BFA', 'Bachelor', 3, 140),
('crs_art_ind', 'fac_art', 'Bachelor of Industrial and Commercial Art', 'BICA', 'Bachelor', 3, 140),
('crs_art_vis', 'fac_art', 'Bachelor of Visual Communication (Graphics)', 'BVCG', 'Bachelor', 3, 140);

-- 4. Insert 20 Sample Lecturers
INSERT INTO users (id, username, password, email, role, full_name, phone, status) VALUES
('usr_lec_001', 'dr.okello', 'lecturer123', 'dr.okello@kqu.ac.ug', 'LECTURER', 'Dr. Emmanuel Okello', '+256 772 301 901', 'ACTIVE'),
('usr_lec_002', 'dr.atuhaire', 'lecturer123', 'dr.atuhaire@kqu.ac.ug', 'LECTURER', 'Dr. Julian Atuhaire', '+256 772 301 902', 'ACTIVE'),
('usr_lec_003', 'mr.tumusiime', 'lecturer123', 'mr.tumusiime@kqu.ac.ug', 'LECTURER', 'Mr. Kenneth Tumusiime', '+256 772 301 903', 'ACTIVE'),
('usr_lec_004', 'ms.natukunda', 'lecturer123', 'ms.natukunda@kqu.ac.ug', 'LECTURER', 'Ms. Brenda Natukunda', '+256 772 301 904', 'ACTIVE'),
('usr_lec_005', 'dr.mukwaya', 'lecturer123', 'dr.mukwaya@kqu.ac.ug', 'LECTURER', 'Dr. Joseph Mukwaya', '+256 772 301 905', 'ACTIVE'),
('usr_lec_006', 'dr.namatovu', 'lecturer123', 'dr.namatovu@kqu.ac.ug', 'LECTURER', 'Dr. Beatrice Namatovu', '+256 772 301 906', 'ACTIVE'),
('usr_lec_007', 'dr.lwanga', 'lecturer123', 'dr.lwanga@kqu.ac.ug', 'LECTURER', 'Dr. Charles Lwanga', '+256 772 301 907', 'ACTIVE'),
('usr_lec_008', 'eng.byaruhanga', 'lecturer123', 'eng.byaruhanga@kqu.ac.ug', 'LECTURER', 'Eng. Moses Byaruhanga', '+256 772 301 908', 'ACTIVE'),
('usr_lec_009', 'eng.nabukenya', 'lecturer123', 'eng.nabukenya@kqu.ac.ug', 'LECTURER', 'Eng. Grace Nabukenya', '+256 772 301 909', 'ACTIVE'),
('usr_lec_010', 'eng.kato', 'lecturer123', 'eng.kato@kqu.ac.ug', 'LECTURER', 'Eng. Isaac Kato', '+256 772 301 910', 'ACTIVE'),
('usr_lec_011', 'dr.akullo', 'lecturer123', 'dr.akullo@kqu.ac.ug', 'LECTURER', 'Dr. Christine Akullo', '+256 772 301 911', 'ACTIVE'),
('usr_lec_012', 'dr.sekatawa', 'lecturer123', 'dr.sekatawa@kqu.ac.ug', 'LECTURER', 'Dr. David Sekatawa', '+256 772 301 912', 'ACTIVE'),
('usr_lec_013', 'dr.birungi', 'lecturer123', 'dr.birungi@kqu.ac.ug', 'LECTURER', 'Dr. Stella Birungi', '+256 772 301 913', 'ACTIVE'),
('usr_lec_014', 'cpa.katusabe', 'lecturer123', 'cpa.katusabe@kqu.ac.ug', 'LECTURER', 'CPA Miriam Katusabe', '+256 772 301 914', 'ACTIVE'),
('usr_lec_015', 'dr.lubega', 'lecturer123', 'dr.lubega@kqu.ac.ug', 'LECTURER', 'Dr. Henry Lubega', '+256 772 301 915', 'ACTIVE'),
('usr_lec_016', 'dr.mayanja', 'lecturer123', 'dr.mayanja@kqu.ac.ug', 'LECTURER', 'Dr. Peter Mayanja', '+256 772 301 916', 'ACTIVE'),
('usr_lec_017', 'adv.namutebi', 'lecturer123', 'adv.namutebi@kqu.ac.ug', 'LECTURER', 'Adv. Brenda Namutebi', '+256 772 301 917', 'ACTIVE'),
('usr_lec_018', 'dr.kyomugisha', 'lecturer123', 'dr.kyomugisha@kqu.ac.ug', 'LECTURER', 'Dr. Martha Kyomugisha', '+256 772 301 918', 'ACTIVE'),
('usr_lec_019', 'mr.okot', 'lecturer123', 'mr.okot@kqu.ac.ug', 'LECTURER', 'Mr. Simon Peter Okot', '+256 772 301 919', 'ACTIVE'),
('usr_lec_020', 'mr.kerchan', 'lecturer123', 'mr.kerchan@kqu.ac.ug', 'LECTURER', 'Mr. Ronald Kerchan', '+256 772 301 920', 'ACTIVE');

INSERT INTO lecturers (id, user_id, staff_id, faculty_id, department, title, specialization, qualification) VALUES
('lec_001', 'usr_lec_001', 'KQU-LEC-001', 'fac_comp', 'Computer Science', 'Dr.', 'Java Enterprise Architecture', 'PhD Computer Science'),
('lec_002', 'usr_lec_002', 'KQU-LEC-002', 'fac_comp', 'Software Engineering', 'Dr.', 'Software Quality & Clean Architecture', 'PhD Software Eng'),
('lec_003', 'usr_lec_003', 'KQU-LEC-003', 'fac_comp', 'Information Technology', 'Mr.', 'Enterprise Relational Databases', 'MSc Information Systems'),
('lec_004', 'usr_lec_004', 'KQU-LEC-004', 'fac_comp', 'Information Technology', 'Ms.', 'Cloud & Web Engineering', 'MSc Computer Science'),
('lec_005', 'usr_lec_005', 'KQU-LEC-005', 'fac_health', 'Clinical Medicine', 'Dr.', 'Clinical Gross Anatomy', 'MBChB, MMed'),
('lec_006', 'usr_lec_006', 'KQU-LEC-006', 'fac_health', 'Pharmacy', 'Dr.', 'Pharmacology & Therapeutics', 'BPharm, PhD'),
('lec_007', 'usr_lec_007', 'KQU-LEC-007', 'fac_health', 'Dental Surgery', 'Dr.', 'Maxillofacial Surgery', 'BDS, MDS'),
('lec_008', 'usr_lec_008', 'KQU-LEC-008', 'fac_eng', 'Civil Engineering', 'Eng.', 'Structural Mechanics', 'MSc Structural Eng, REng'),
('lec_009', 'usr_lec_009', 'KQU-LEC-009', 'fac_eng', 'Electrical Engineering', 'Eng.', 'Power Systems & Electronics', 'MSc Electrical Eng, REng'),
('lec_010', 'usr_lec_010', 'KQU-LEC-010', 'fac_eng', 'Mechanical Engineering', 'Eng.', 'Thermodynamics & Energy', 'PhD Mechanical Eng'),
('lec_011', 'usr_lec_011', 'KQU-LEC-011', 'fac_agric', 'Crop Science', 'Dr.', 'Soil Agronomy & Plant Health', 'PhD Agric Sciences'),
('lec_012', 'usr_lec_012', 'KQU-LEC-012', 'fac_agric', 'Food Science', 'Dr.', 'Food Microbiology & Postharvest', 'PhD Food Biotechnology'),
('lec_013', 'usr_lec_013', 'KQU-LEC-013', 'fac_bus', 'Business Administration', 'Dr.', 'Strategic Management', 'DBA'),
('lec_014', 'usr_lec_014', 'KQU-LEC-014', 'fac_bus', 'Accounting & Finance', 'CPA', 'Financial Auditing & Reporting', 'MBA, CPA'),
('lec_015', 'usr_lec_015', 'KQU-LEC-015', 'fac_bus', 'Economics', 'Dr.', 'Macroeconomics & Econometrics', 'PhD Economics'),
('lec_016', 'usr_lec_016', 'KQU-LEC-016', 'fac_law', 'Public Law', 'Dr.', 'Constitutional Jurisprudence', 'LL.B, LL.M, PhD'),
('lec_017', 'usr_lec_017', 'KQU-LEC-017', 'fac_law', 'Commercial Law', 'Adv.', 'Corporate Contracts & IP', 'LL.B, LL.M'),
('lec_018', 'usr_lec_018', 'KQU-LEC-018', 'fac_edu', 'Science Education', 'Dr.', 'Pedagogy & Educational Psychology', 'PhD Educational Psychology'),
('lec_019', 'usr_lec_019', 'KQU-LEC-019', 'fac_edu', 'Humanities Education', 'Mr.', 'Curriculum Evaluation', 'M.Ed Curriculum Studies'),
('lec_020', 'usr_lec_020', 'KQU-LEC-020', 'fac_art', 'Fine Art', 'Mr.', 'African Aesthetics & Studio Art', 'MFA');

-- 5. Insert Sample Student Records (KQU/2024/001 - KQU/2024/005 shown, generator expands to 500)
INSERT INTO users (id, username, password, email, role, full_name, phone, status) VALUES
('usr_std_001', 'kqu24001', 'student123', 'std.samuel.mukasa1@kqu.ac.ug', 'STUDENT', 'Samuel Mukasa', '+256 754 111 222', 'ACTIVE'),
('usr_std_002', 'kqu24002', 'student123', 'std.sarah.namubiru2@kqu.ac.ug', 'STUDENT', 'Sarah Namubiru', '+256 754 111 223', 'ACTIVE'),
('usr_std_003', 'kqu24003', 'student123', 'std.emmanuel.ssenkumba3@kqu.ac.ug', 'STUDENT', 'Emmanuel Ssenkumba', '+256 754 111 224', 'ACTIVE'),
('usr_std_004', 'kqu24004', 'student123', 'std.grace.kigozi4@kqu.ac.ug', 'STUDENT', 'Grace Kigozi', '+256 754 111 225', 'ACTIVE'),
('usr_std_005', 'kqu24005', 'student123', 'std.brian.nakitto5@kqu.ac.ug', 'STUDENT', 'Brian Nakitto', '+256 754 111 226', 'ACTIVE');

INSERT INTO students (id, user_id, reg_number, faculty_id, course_id, year_of_study, current_semester, academic_year, gender, cgpa, attendance_rate) VALUES
('std_rec_001', 'usr_std_001', 'KQU/2024/001', 'fac_comp', 'crs_cs', 1, 1, '2024/2025', 'MALE', 4.45, 92.5),
('std_rec_002', 'usr_std_002', 'KQU/2024/002', 'fac_health', 'crs_mbchb', 2, 1, '2024/2025', 'FEMALE', 4.60, 96.0),
('std_rec_003', 'usr_std_003', 'KQU/2024/003', 'fac_eng', 'crs_civ', 1, 1, '2024/2025', 'MALE', 3.92, 85.0),
('std_rec_004', 'usr_std_004', 'KQU/2024/004', 'fac_law', 'crs_llb', 3, 1, '2024/2025', 'FEMALE', 4.20, 90.0),
('std_rec_005', 'usr_std_005', 'KQU/2024/005', 'fac_bus', 'crs_bba', 1, 1, '2024/2025', 'MALE', 4.10, 88.0);
