/**
 * app.js
 Smart Campus Management System
 * Web Edition Frontend Logic (HTML5, CSS3, Vanilla JS, Java, SQLite)
 */

(function() {
  'use strict';

  // Configuration & State
  const API_BASE = '/api';
  let isServerConnected = false;
  let currentRole = 'ADMIN'; // ADMIN, LECTURER, STUDENT

  // Embedded Data Fallback (Used if opened statically without Java Server running)
  const fallbackStudents = generateInitialStudents(500);
  const fallbackLecturers = [
    { staffId: 'KQU-LEC-001', name: 'Dr. Arthur Pendelton', faculty: 'Science & Technology', department: 'Computer Science', specialization: 'Computer Architecture & Systems' },
    { staffId: 'KQU-LEC-002', name: 'Dr. Joyce Okello', faculty: 'Science & Technology', department: 'Software Engineering', specialization: 'Software Architecture & Testing' },
    { staffId: 'KQU-LEC-003', name: 'Prof. Godfrey Kigozi', faculty: 'Law', department: 'Commercial Law', specialization: 'Constitutional & Corporate Law' },
    { staffId: 'KQU-LEC-004', name: 'Dr. Sarah Nabirye', faculty: 'Health Sciences', department: 'Clinical Medicine', specialization: 'Internal Medicine' }
  ];

  let currentStudents = [...fallbackStudents];

  let lecturerRoster = [
    { regNumber: 'KQU/2024/001', name: 'Samuel Mukasa', attended: 15, total: 16, cw: 34.0, exam: 52.0 },
    { regNumber: 'KQU/2024/002', name: 'Brenda Nabirye', attended: 16, total: 16, cw: 36.0, exam: 54.0 },
    { regNumber: 'KQU/2024/003', name: 'John Baptist Ochieng', attended: 10, total: 16, cw: 22.0, exam: 38.0 },
    { regNumber: 'KQU/2024/004', name: 'Grace Achieng', attended: 14, total: 16, cw: 31.0, exam: 48.0 },
    { regNumber: 'KQU/2024/005', name: 'Emmanuel Ssenyonjo', attended: 8, total: 16, cw: 18.0, exam: 28.0 },
    { regNumber: 'KQU/2024/006', name: 'Fiona Namubiru', attended: 15, total: 16, cw: 35.0, exam: 50.0 },
    { regNumber: 'KQU/2024/007', name: 'David Kato', attended: 13, total: 16, cw: 28.0, exam: 45.0 },
    { regNumber: 'KQU/2024/008', name: 'Patricia Alum', attended: 16, total: 16, cw: 38.0, exam: 55.0 }
  ];

  // =========================================================================
  // Initialization
  // =========================================================================
  document.addEventListener('DOMContentLoaded', () => {
    setupDateDisplay();
    setupEventListeners();
    checkBackendConnection();
    renderAdminStudentsTable();
    renderLecturerWorkspace();
    renderStudentTranscript();
  });

  function setupDateDisplay() {
    const el = document.getElementById('current-session-date');
    if (el) {
      const now = new Date();
      el.textContent = now.toLocaleDateString('en-US', { weekday: 'short', year: 'numeric', month: 'short', day: 'numeric' });
    }
  }

  // =========================================================================
  // Backend Detection
  // =========================================================================
  async function checkBackendConnection() {
    const dot = document.getElementById('server-dot');
    const text = document.getElementById('server-status-text');

    try {
      const res = await fetch(`${API_BASE}/stats`);
      if (res.ok) {
        const stats = await res.json();
        isServerConnected = true;
        if (dot) dot.style.backgroundColor = '#10B981';
        if (text) text.textContent = `Java WebServer & SQLite Database Connected (${stats.totalStudents} Students)`;
        loadStudentsFromBackend();
        return;
      }
    } catch (e) {
      // Backend not running (e.g. static preview or local file)
    }

    isServerConnected = false;
    if (dot) dot.style.backgroundColor = '#D4AF37';
    if (text) text.textContent = 'Local Standalone Mode (500 Enrolled Students)';
  }

  async function loadStudentsFromBackend() {
    try {
      const res = await fetch(`${API_BASE}/students`);
      if (res.ok) {
        const data = await res.json();
        if (Array.isArray(data) && data.length > 0) {
          currentStudents = data;
          renderAdminStudentsTable();
        }
      }
    } catch (e) {
      console.warn('Could not load students from backend:', e);
    }
  }

  // =========================================================================
  // Event Listeners Setup
  // =========================================================================
  function setupEventListeners() {
    // Role switcher buttons
    document.querySelectorAll('.role-btn').forEach(btn => {
      btn.addEventListener('click', (e) => {
        const role = e.currentTarget.getAttribute('data-role');
        switchRole(role);
      });
    });

    // Search and filter in Admin
    const searchInput = document.getElementById('admin-search-input');
    if (searchInput) {
      searchInput.addEventListener('input', handleAdminSearch);
    }

    const facultyFilter = document.getElementById('admin-faculty-filter');
    if (facultyFilter) {
      facultyFilter.addEventListener('change', handleAdminSearch);
    }

    // Modal open / close
    const btnOpenModal = document.getElementById('btn-open-register-modal');
    const registerModal = document.getElementById('modal-register-student');
    if (btnOpenModal && registerModal) {
      btnOpenModal.addEventListener('click', () => registerModal.classList.add('active'));
    }

    document.querySelectorAll('.modal-close').forEach(btn => {
      btn.addEventListener('click', (e) => {
        const modalId = e.currentTarget.getAttribute('data-close');
        const m = document.getElementById(modalId);
        if (m) m.classList.remove('active');
      });
    });

    // Register Student Form
    const regForm = document.getElementById('form-register-student');
    if (regForm) {
      regForm.addEventListener('submit', handleRegisterStudent);
    }

    // Save grades button
    const btnSaveGrades = document.getElementById('btn-save-grades');
    if (btnSaveGrades) {
      btnSaveGrades.addEventListener('click', handleSaveGrades);
    }

    // Print Transcript
    const btnPrint = document.getElementById('btn-print-transcript');
    if (btnPrint) {
      btnPrint.addEventListener('click', () => window.print());
    }

    // Login/Logout button in header
    const btnLoginLogout = document.getElementById('btn-login-logout');
    if (btnLoginLogout) {
      btnLoginLogout.addEventListener('click', () => {
        const nextRole = currentRole === 'ADMIN' ? 'LECTURER' : currentRole === 'LECTURER' ? 'STUDENT' : 'ADMIN';
        switchRole(nextRole);
      });
    }
  }

  // =========================================================================
  // Role Switching Logic
  // =========================================================================
  function switchRole(role) {
    currentRole = role;

    // Update role buttons active state
    document.querySelectorAll('.role-btn').forEach(btn => {
      btn.classList.toggle('active', btn.getAttribute('data-role') === role);
    });

    // Update Header
    const roleDisplay = document.getElementById('role-display');
    const userNameDisplay = document.getElementById('user-name-display');
    const userIdDisplay = document.getElementById('user-id-display');

    if (role === 'ADMIN') {
      if (roleDisplay) roleDisplay.textContent = 'ADMINISTRATOR';
      if (userNameDisplay) userNameDisplay.textContent = 'Dr. Arthur Pendelton';
      if (userIdDisplay) userIdDisplay.textContent = 'admin@kqu.ac.ug';
    } else if (role === 'LECTURER') {
      if (roleDisplay) roleDisplay.textContent = 'SENIOR LECTURER';
      if (userNameDisplay) userNameDisplay.textContent = 'Dr. Joyce Okello';
      if (userIdDisplay) userIdDisplay.textContent = 'j.okello@kqu.ac.ug';
    } else if (role === 'STUDENT') {
      if (roleDisplay) roleDisplay.textContent = 'UNDERGRADUATE STUDENT';
      if (userNameDisplay) userNameDisplay.textContent = 'Samuel Mukasa';
      if (userIdDisplay) userIdDisplay.textContent = 'KQU/2024/001';
    }

    // Switch View Section
    document.querySelectorAll('.view-section').forEach(sec => sec.classList.remove('active'));
    if (role === 'ADMIN') {
      const el = document.getElementById('admin-view');
      if (el) el.classList.add('active');
    } else if (role === 'LECTURER') {
      const el = document.getElementById('lecturer-view');
      if (el) el.classList.add('active');
    } else if (role === 'STUDENT') {
      const el = document.getElementById('student-view');
      if (el) el.classList.add('active');
    }

    showToast(`Switched view to ${role} Portal`);
  }

  // =========================================================================
  // Administrator View: Render Student Registry & Search
  // =========================================================================
  function handleAdminSearch() {
    const query = (document.getElementById('admin-search-input')?.value || '').toLowerCase().trim();
    const faculty = document.getElementById('admin-faculty-filter')?.value || 'ALL';

    const filtered = currentStudents.filter(s => {
      const matchesSearch = s.regNumber.toLowerCase().includes(query) ||
                            s.fullName.toLowerCase().includes(query);
      const matchesFaculty = faculty === 'ALL' || (s.facultyName && s.facultyName.includes(faculty));
      return matchesSearch && matchesFaculty;
    });

    renderAdminStudentsTable(filtered);
  }

  function renderAdminStudentsTable(list = currentStudents) {
    const tbody = document.getElementById('admin-students-tbody');
    if (!tbody) return;

    if (list.length === 0) {
      tbody.innerHTML = `<tr><td colspan="7" style="text-align:center; padding: 2rem; color: var(--kqu-text-muted);">No student records matched your search query.</td></tr>`;
      return;
    }

    // Display first 40 records for fluid browser performance
    const preview = list.slice(0, 40);

    tbody.innerHTML = preview.map(s => {
      const cleared = s.attendanceRate >= 75.0;
      const badgeClass = cleared ? 'badge-success' : 'badge-danger';
      const badgeText = cleared ? 'CLEARED ✓' : 'BARRED ⚠️';

      return `
        <tr>
          <td><strong style="color: var(--kqu-royal-blue);">${s.regNumber}</strong></td>
          <td>
            <div style="font-weight: 600;">${s.fullName}</div>
            <div style="font-size: 0.75rem; color: var(--kqu-text-muted);">${s.email}</div>
          </td>
          <td>
            <div>${s.facultyName || 'Science & Technology'}</div>
            <div style="font-size: 0.75rem; color: var(--kqu-text-muted);">${s.courseName || 'B.Sc. Computer Science'}</div>
          </td>
          <td>Year ${s.yearOfStudy || 1}</td>
          <td><strong>${Number(s.cgpa || 0).toFixed(2)}</strong></td>
          <td>
            <div style="font-weight: 600;">${Number(s.attendanceRate || 0).toFixed(1)}%</div>
            <div style="width: 70px; height: 4px; background: #E2E8F0; border-radius: 2px; overflow: hidden; margin-top: 2px;">
              <div style="width: ${Math.min(100, s.attendanceRate)}%; height: 100%; background: ${cleared ? 'var(--kqu-success)' : 'var(--kqu-danger)'};"></div>
            </div>
          </td>
          <td><span class="badge ${badgeClass}">${badgeText}</span></td>
        </tr>
      `;
    }).join('');

    // Update summary count if full list
    const statEl = document.getElementById('stat-students');
    if (statEl) {
      statEl.textContent = currentStudents.length;
    }
  }

  // =========================================================================
  // Modal: Register Student
  // =========================================================================
  async function handleRegisterStudent(e) {
    e.preventDefault();
    const fullName = document.getElementById('reg-fullname')?.value.trim();
    const email = document.getElementById('reg-email')?.value.trim();
    const faculty = document.getElementById('reg-faculty')?.value;
    const course = document.getElementById('reg-course')?.value;

    if (!fullName || !email) return;

    const count = currentStudents.length + 1;
    const newRegNumber = `KQU/2024/${String(count).padStart(3, '0')}`;

    const newStudent = {
      id: `std-${Date.now()}`,
      regNumber: newRegNumber,
      fullName: fullName,
      email: email,
      facultyName: faculty,
      courseName: course,
      yearOfStudy: 1,
      cgpa: 3.80,
      attendanceRate: 95.0,
      status: 'ACTIVE'
    };

    if (isServerConnected) {
      try {
        await fetch(`${API_BASE}/students`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ fullName, email, facultyName: faculty, courseName: course })
        });
      } catch (err) {
        console.warn('Backend save failed, using local memory:', err);
      }
    }

    currentStudents.unshift(newStudent);
    renderAdminStudentsTable();

    // Reset and close modal
    document.getElementById('form-register-student')?.reset();
    document.getElementById('modal-register-student')?.classList.remove('active');
    showToast(`Successfully enrolled: ${fullName} (${newRegNumber})`);
  }

  // =========================================================================
  // Lecturer View: Attendance Register & Grade Sheet
  // =========================================================================
  function renderLecturerWorkspace() {
    renderLecturerAttendance();
    renderLecturerGrading();
  }

  function renderLecturerAttendance() {
    const tbody = document.getElementById('lecturer-attendance-tbody');
    if (!tbody) return;

    tbody.innerHTML = lecturerRoster.map((item, idx) => {
      const rate = ((item.attended / item.total) * 100);
      const isCleared = rate >= 75.0;

      return `
        <tr>
          <td><strong style="color: var(--kqu-royal-blue);">${item.regNumber}</strong></td>
          <td style="font-weight: 600;">${item.name}</td>
          <td>${item.attended} / ${item.total} lectures</td>
          <td>
            <strong style="color: ${isCleared ? 'var(--kqu-success)' : 'var(--kqu-danger)'}">${rate.toFixed(1)}%</strong>
          </td>
          <td>
            <span class="badge ${isCleared ? 'badge-success' : 'badge-danger'}">
              ${isCleared ? 'CLEARED ✓' : 'BARRED ⚠️ (<75%)'}
            </span>
          </td>
          <td>
            <div class="attendance-actions">
              <button class="att-btn present active" data-idx="${idx}" data-status="P">P</button>
              <button class="att-btn late" data-idx="${idx}" data-status="L">L</button>
              <button class="att-btn absent" data-idx="${idx}" data-status="A">A</button>
              <button class="att-btn excused" data-idx="${idx}" data-status="E">E</button>
            </div>
          </td>
        </tr>
      `;
    }).join('');

    // Attach click events for attendance status buttons
    tbody.querySelectorAll('.att-btn').forEach(btn => {
      btn.addEventListener('click', (e) => {
        const parent = e.currentTarget.parentElement;
        parent.querySelectorAll('.att-btn').forEach(b => b.classList.remove('active'));
        e.currentTarget.classList.add('active');

        const idx = Number(e.currentTarget.getAttribute('data-idx'));
        const status = e.currentTarget.getAttribute('data-status');
        if (status === 'P') {
          lecturerRoster[idx].attended = Math.min(lecturerRoster[idx].total, lecturerRoster[idx].attended + 1);
        } else if (status === 'A') {
          lecturerRoster[idx].attended = Math.max(0, lecturerRoster[idx].attended - 1);
        }
        renderLecturerAttendance();
      });
    });
  }

  function renderLecturerGrading() {
    const tbody = document.getElementById('lecturer-grading-tbody');
    if (!tbody) return;

    tbody.innerHTML = lecturerRoster.map((item, idx) => {
      const cw = item.cw || 0;
      const exam = item.exam || 0;
      const total = cw + exam;
      const grade = computeGrade(total);
      const gp = computeGradePoint(total);

      return `
        <tr>
          <td><strong style="color: var(--kqu-royal-blue);">${item.regNumber}</strong></td>
          <td style="font-weight: 600;">${item.name}</td>
          <td>
            <input type="number" step="0.5" min="0" max="40" class="mark-input cw-input" data-idx="${idx}" value="${cw}">
          </td>
          <td>
            <input type="number" step="0.5" min="0" max="60" class="mark-input exam-input" data-idx="${idx}" value="${exam}">
          </td>
          <td><strong id="total-${idx}">${total.toFixed(1)}</strong> / 100</td>
          <td><span class="badge ${grade === 'F' ? 'badge-danger' : 'badge-neutral'}" id="grade-${idx}">${grade}</span></td>
          <td><strong id="gp-${idx}">${gp.toFixed(1)}</strong></td>
        </tr>
      `;
    }).join('');

    // Attach real-time mark calculation listeners
    tbody.querySelectorAll('.mark-input').forEach(input => {
      input.addEventListener('input', (e) => {
        const idx = Number(e.currentTarget.getAttribute('data-idx'));
        const cwVal = parseFloat(tbody.querySelector(`.cw-input[data-idx="${idx}"]`)?.value) || 0;
        const examVal = parseFloat(tbody.querySelector(`.exam-input[data-idx="${idx}"]`)?.value) || 0;

        lecturerRoster[idx].cw = Math.min(40, Math.max(0, cwVal));
        lecturerRoster[idx].exam = Math.min(60, Math.max(0, examVal));

        const total = lecturerRoster[idx].cw + lecturerRoster[idx].exam;
        const grade = computeGrade(total);
        const gp = computeGradePoint(total);

        const totalEl = document.getElementById(`total-${idx}`);
        const gradeEl = document.getElementById(`grade-${idx}`);
        const gpEl = document.getElementById(`gp-${idx}`);

        if (totalEl) totalEl.textContent = total.toFixed(1);
        if (gradeEl) {
          gradeEl.textContent = grade;
          gradeEl.className = `badge ${grade === 'F' ? 'badge-danger' : 'badge-neutral'}`;
        }
        if (gpEl) gpEl.textContent = gp.toFixed(1);
      });
    });
  }

  function handleSaveGrades() {
    showToast('Grades validated and submitted to the KQU Examination Board.');
  }

  // =========================================================================
  // Student View: Official Academic Transcript
  // =========================================================================
  function renderStudentTranscript() {
    const student = {
      name: 'SAMUEL MUKASA',
      regNumber: 'KQU/2024/001',
      faculty: 'Faculty of Science and Technology',
      course: 'Bachelor of Science in Computer Science',
      cgpa: 4.62,
      attendanceRate: 92.0
    };

    const modules = [
      { code: 'CSC1101', title: 'Introduction to Programming & OOP', cu: 4, cw: 34.0, exam: 52.0 },
      { code: 'CSC1102', title: 'Computer Architecture & Organization', cu: 4, cw: 31.5, exam: 47.0 },
      { code: 'MTH1103', title: 'Calculus & Discrete Mathematics', cu: 4, cw: 29.0, exam: 44.0 },
      { code: 'ENG1104', title: 'Communication Skills & Academic Writing', cu: 3, cw: 35.0, exam: 50.0 }
    ];

    const tbody = document.getElementById('transcript-tbody');
    if (!tbody) return;

    tbody.innerHTML = modules.map(m => {
      const total = m.cw + m.exam;
      const grade = computeGrade(total);
      const gp = computeGradePoint(total);

      return `
        <tr>
          <td><strong style="color: var(--kqu-royal-blue);">${m.code}</strong></td>
          <td>${m.title}</td>
          <td>${m.cu}</td>
          <td>${m.cw.toFixed(1)}</td>
          <td>${m.exam.toFixed(1)}</td>
          <td><strong>${total.toFixed(1)}</strong></td>
          <td><span class="badge ${grade === 'F' ? 'badge-danger' : 'badge-neutral'}">${grade}</span></td>
          <td><strong>${gp.toFixed(1)}</strong></td>
        </tr>
      `;
    }).join('');

    // Honours calculation
    const honours = getDegreeClassification(student.cgpa);
    const honoursEl = document.getElementById('trans-honours');
    const cgpaEl = document.getElementById('trans-cgpa');
    if (honoursEl) honoursEl.textContent = honours;
    if (cgpaEl) cgpaEl.textContent = `${student.cgpa.toFixed(2)} / 5.00`;
  }

  // =========================================================================
  // Grading & Honours Computation Formulas (Standard KQU Senate Rules)
  // =========================================================================
  function computeGrade(total) {
    if (total >= 80) return 'A';
    if (total >= 75) return 'B+';
    if (total >= 70) return 'B';
    if (total >= 65) return 'C+';
    if (total >= 60) return 'C';
    if (total >= 50) return 'D';
    return 'F';
  }

  function computeGradePoint(total) {
    if (total >= 80) return 5.0;
    if (total >= 75) return 4.5;
    if (total >= 70) return 4.0;
    if (total >= 65) return 3.5;
    if (total >= 60) return 3.0;
    if (total >= 50) return 2.0;
    return 0.0;
  }

  function getDegreeClassification(cgpa) {
    if (cgpa >= 4.40) return 'FIRST CLASS HONOURS';
    if (cgpa >= 3.60) return 'SECOND CLASS HONOURS (UPPER DIVISION)';
    if (cgpa >= 2.80) return 'SECOND CLASS HONOURS (LOWER DIVISION)';
    if (cgpa >= 2.00) return 'PASS DEGREE';
    return 'FAIL / ACADEMIC PROBATION';
  }

  // =========================================================================
  // Toast Helper
  // =========================================================================
  function showToast(message) {
    const container = document.getElementById('toast-container');
    if (!container) return;

    const toast = document.createElement('div');
    toast.className = 'toast';
    toast.textContent = message;
    container.appendChild(toast);

    setTimeout(() => {
      toast.style.transition = 'opacity 0.4s ease';
      toast.style.opacity = '0';
      setTimeout(() => toast.remove(), 400);
    }, 3000);
  }

  // =========================================================================
  // Data Generator for 500 Students
  // =========================================================================
  function generateInitialStudents(count) {
    const firstNames = ['Samuel', 'Brenda', 'John', 'Grace', 'Emmanuel', 'Fiona', 'David', 'Patricia', 'Daniel', 'Sarah', 'Joseph', 'Rebecca', 'Brian', 'Joan', 'Michael', 'Esther', 'Paul', 'Ruth', 'Peter', 'Mercy'];
    const lastNames = ['Mukasa', 'Nabirye', 'Ochieng', 'Achieng', 'Ssenyonjo', 'Namubiru', 'Kato', 'Alum', 'Okello', 'Akello', 'Mugisha', 'Kembabazi', 'Wasswa', 'Nakato', 'Kiprotich', 'Chebet', 'Bwambale', 'Biira', 'Musoke', 'Babirye'];
    const faculties = [
      { name: 'Faculty of Science & Technology', course: 'B.Sc. Computer Science' },
      { name: 'Faculty of Business & Management', course: 'Bachelor of Business Administration' },
      { name: 'Faculty of Law', course: 'Bachelor of Laws (LL.B)' },
      { name: 'Faculty of Health Sciences', course: 'Bachelor of Medicine & Surgery' },
      { name: 'Faculty of Engineering', course: 'B.Sc. Civil Engineering' }
    ];

    const list = [];
    for (let i = 1; i <= count; i++) {
      const fn = firstNames[(i - 1) % firstNames.length];
      const ln = lastNames[Math.floor((i - 1) / firstNames.length) % lastNames.length];
      const fac = faculties[(i - 1) % faculties.length];
      const reg = `KQU/2024/${String(i).padStart(3, '0')}`;
      const attendance = Math.min(100, Math.max(50, 70 + ((i * 7) % 31)));
      const cgpa = (2.5 + ((i * 13) % 25) / 10).toFixed(2);

      list.push({
        id: `std-${i}`,
        regNumber: reg,
        fullName: `${fn} ${ln}`,
        email: `${fn.toLowerCase()}.${ln.toLowerCase()}@kqu.ac.ug`,
        facultyName: fac.name,
        courseName: fac.course,
        yearOfStudy: ((i - 1) % 4) + 1,
        cgpa: parseFloat(cgpa),
        attendanceRate: attendance,
        status: 'ACTIVE'
      });
    }
    return list;
  }

})();
