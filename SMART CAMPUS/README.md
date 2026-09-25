
## Smart Campus Student Management and Academic Information System
**Technology:** Java, JDBC / SQLite & MySQL, Object-Oriented Programming (OOP), Swing/Web  
**Coursework Submission:** e-campus & GitHub Repository  

---

### 1. Project Overview & Main Objective
The **Smart Campus Student Management and Academic Information System** automates student registration, course allocations, daily lecture attendance tracking, coursework & exam grading, academic progression, and official transcript generation for **King's and Queen's University**.

### 2. University Branding & Color Palette
The system strictly enforces the university's royal heraldic palette:
- **Royal Blue (`#0F2557`):** Academic dignity, institutional authority, header bars.
- **Gold (`#D4AF37`):** Excellence, honours classification, accents and primary call-to-actions.
- **Warm Cream (`#FAF7EE` / `#FFFDD0`):** Parchment background, high legibility, reduced eye-strain.
- **Black (`#0B0F19`):** High-contrast typography and structural framing.

---

### 3. Academic Structure (8 Faculties & Courses)
1. **Faculty of Health Sciences / Medicine:**
   - Bachelor of Medicine and Bachelor of Surgery (MBChB): 5 Years
   - Bachelor of Dental Surgery (BDS): 5 Years
   - Bachelor of Pharmacy (PHA): 4 Years
   - Bachelor of Nursing Science (Direct): 4 Years
   - Bachelor of Science in Public Health: 3 Years

2. **Faculty of Engineering, Technology & Design:**
   - Bachelor of Civil Engineering: 4 Years
   - Bachelor of Electrical Engineering: 4 Years
   - Bachelor of Mechanical Engineering: 4 Years
   - Bachelor of Science in Biosystems Engineering: 4 Years
   - Bachelor of Architecture: 5 Years
   - Bachelor of Science in Land Surveying and Geomatics: 4 Years

3. **Faculty of Computing & Information Science:**
   - Bachelor of Science in Computer Science: 3 Years
   - Bachelor of Science in Software Engineering: 4 Years
   - Bachelor of Information Technology (IT): 3 Years
   - Bachelor of Business Computing: 3 Years
   - Bachelor of Computer Science with Education: 3 Years
   - Bachelor of Library and Information Science: 3 Years
   - Bachelor of Records and Information Management: 3 Years
   - Bachelor of Office and Information Management: 3 Years
   - Diploma in Library & Information Science: 2 Years
   - Diploma in Computer Science & Information Technology: 2 Years

4. **Faculty of Agriculture & Animal Sciences:**
   - Bachelor of Science in Agriculture: 4 Years
   - Bachelor of Science in Food Bioscience & Agribusiness: 4 Years
   - Bachelor of Science in Food Science and Technology: 4 Years
   - Bachelor of Animal Production and Management: 3 Years
   - Bachelor of Science in Horticulture: 3 Years
   - Diploma in Animal Production & Management: 2 Years
   - Diploma in Crop Production & Management: 2 Years

5. **Faculty of Business, Economics & Management:**
   - Bachelor of Business Administration (BBA): 3 Years
   - Bachelor of Commerce (BCom): 3 Years
   - Bachelor of Arts in Economics: 3 Years
   - Bachelor of Science in Accounting and Finance: 3 Years
   - Bachelor of Entrepreneurship & Small Business Management: 3 Years
   - Bachelor of Procurement and Supply Chain Management: 3 Years
   - Bachelor of Tourism and Hospitality Management: 3 Years
   - Diploma in Accounting and Finance: 2 Years
   - Diploma in Business Intelligence and Data Analytics: 2 Years
   - Diploma in Microfinance: 2 Years (Distance Learning)

6. **Faculty of Law:**
   - Bachelor of Laws (LL.B): 5 Years
   - Diploma in Law: 3 Years

7. **Faculty of Education & Humanities:**
   - Bachelor of Science with Education (Biological): 3 Years
   - Bachelor of Science with Education (Physical): 3 Years
   - Bachelor of Science with Education (Mathematics & Economics): 3 Years
   - Bachelor of Science in Education (Chemistry): 3 Years
   - Bachelor of Science in Education (Geography): 3 Years
   - Bachelor of Science in Education (Physics): 3 Years
   - Bachelor of Science in Education (Mathematics): 3 Years
   - Bachelor of Science Education (Sport and Exercise Science): 3 Years
   - Bachelor of Early Childhood and Pre-Primary Education: 3 Years

8. **School of Art and Industrial Design:**
   - Bachelor of Fine Art: 3 Years
   - Bachelor of Industrial and Commercial Art: 3 Years
   - Bachelor of Visual Communication (Graphics): 3 Years

---

### 4. Key Accounts & User Credentials
- **System Administrator (Full Powers):**
  - Username / Email: `admin@kqu.ac.ug`
  - Password: `admin123`
  - Name: `Prof. Arthur Ssenkumba` (Office of the Academic Registrar)

- **Lecturers (20 Sample Accounts):**
  - e.g. `dr.okello@kqu.ac.ug` / `lecturer123` (Dr. Emmanuel Okello - Computing)
  - e.g. `dr.mukwaya@kqu.ac.ug` / `lecturer123` (Dr. Joseph Mukwaya - Health Sciences)
  - e.g. `eng.byaruhanga@kqu.ac.ug` / `lecturer123` (Eng. Moses Byaruhanga - Engineering)

- **Students (500 Registered Accounts):**
  - Registration Numbers: `KQU/2024/001` through `KQU/2024/500`
  - Password: `student123` or student Reg Number

---

### 5. Grading Scale (5.0 GP Standard)
- 80% - 100%: **A** (5.0 GP) - Excellent
- 75% - 79%: **B+** (4.5 GP) - Very Good
- 70% - 74%: **B** (4.0 GP) - Good
- 65% - 69%: **C+** (3.5 GP) - Fair
- 60% - 64%: **C** (3.0 GP) - Pass
- 50% - 59%: **D** (2.0 GP) - Marginal Pass
- Below 50%: **F** (0.0 GP) - Retake

---

### 6. Running the System in Visual Studio Code (VS Code)

You have **TWO** ways to run the project depending on your preference:

#### **Option A: Web-Based Version (HTML5, CSS3, JavaScript, Java Backend, SQLite Database)**

1. Open the `KingsQueensUniversity` folder in VS Code.
2. Ensure you have the **"Extension Pack for Java"** installed in VS Code.
3. Open `src/main/java/com/kqu/web/WebServer.java`.
4. Click **"Run"** above `public static void main(String[] args)` (or press the green Play button).
5. Open your web browser and go to:
   👉 **`http://localhost:8080`**
6. Alternatively, in the VS Code integrated terminal (`Ctrl + ~`):
   - On Linux/macOS:
     ```bash
     chmod +x run-web.sh && ./run-web.sh
     ```
   - On Windows:
     ```cmd
     run-web.bat
     ```

*Note:* You can also open `web/index.html` directly or with VS Code's "Live Server" extension to inspect the frontend interface!

#### **Option B: Java Desktop Application (Swing GUI + SQLite Database)**

1. Open `src/main/java/com/kqu/Main.java`.
2. Click **"Run"** above `public static void main(String[] args)`.
3. The desktop GUI window will launch directly on your screen.

---

### 7. Compilation & Execution via Terminal (Manual)
```bash
# 1. Compile all Java source files
javac -d bin src/main/java/com/kqu/*.java src/main/java/com/kqu/*/*.java

# 2. Run Web Edition (HTML/CSS/JS + Java Web Server + SQLite)
java -cp bin com.kqu.web.WebServer

# OR Run Desktop Edition (Swing GUI)
java -cp bin com.kqu.Main
```
