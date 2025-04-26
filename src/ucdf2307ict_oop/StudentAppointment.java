package ucdf2307ict_oop;

import java.io.*;
import java.text.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.*;

public class StudentAppointment extends javax.swing.JFrame{
    private String studentID;
    private int row = -1;
    private String[] columnNames = {"Day", "Time", "Lecturer"};
    private DefaultTableModel upcomingAppointmentModel = new DefaultTableModel(columnNames, 0);
    private DefaultTableModel attendedAppointmentModel = new DefaultTableModel(columnNames, 0);
    private File lecSlotsFile = new File("lecturerSlots.txt");
    private File studentAppointmentFile = new File("studentAppointment.txt");
    private File studentPastAppointmentFile = new File("studentPastAppointment.txt");
    private File studentRescheduleFile = new File("studentReschedule.txt");

    // Set the models to the respective tables
    public StudentAppointment(String studentID) {
        this.studentID = studentID;
        upcomingAppointmentModel.setColumnIdentifiers(columnNames);
        attendedAppointmentModel.setColumnIdentifiers(columnNames);
        initComponents();
        if (!studentPastAppointmentFile.exists()) {
            try {
                studentPastAppointmentFile.createNewFile();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error creating file: " + e.getMessage());
            }
        }
        displayAppointment();
    }
    
    private void displayAppointment() {
        try {
            // Clear existing table data
            upcomingAppointmentModel.setRowCount(0);
            attendedAppointmentModel.setRowCount(0);

            // Create lists to store appointments before adding to tables
            List<Object[]> upcomingList = new ArrayList<>();
            List<Object[]> attendedList = new ArrayList<>();

            // Get current date and time
            Date currentDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("EEE (dd/MM/yyyy),hh:mm a");

            // Read all appointments from studentAppointment.txt
            try (BufferedReader br = new BufferedReader(new FileReader(studentAppointmentFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) {
                        continue; // Skip empty lines
                    }

                    String[] appointmentDetails = line.split(",");
                    // Check if this appointment belongs to the current student
                    if (appointmentDetails[0].equals(studentID)) {
                        String day = appointmentDetails[1];
                        String time = appointmentDetails[2];
                        String lecturer = appointmentDetails[3];

                        try {
                            // Create the full date-time for comparison
                            String startTime = time.split(" - ")[0].trim();
                            Date appointmentDate = sdf.parse(day + "," + startTime);

                            // Create row data
                            Object[] rowData = {
                                day,     // Day
                                time,    // Time range
                                lecturer, // Lecturer
                                appointmentDate // For sorting
                            };

                            if (appointmentDate.before(currentDate)) {
                                // Past appointment - add to attended list
                                attendedList.add(rowData);

                                // Append to past appointments file
                                try (FileWriter fw = new FileWriter(studentPastAppointmentFile, true);
                                     BufferedWriter bw = new BufferedWriter(fw)) {
                                    bw.write(studentID + "," + day + "," + time + "," + lecturer);
                                    bw.newLine();
                                }
                            } else {
                                // Future appointment - add to upcoming list
                                upcomingList.add(rowData);
                            }
                        } catch (ParseException e) {
                            System.out.println("Error parsing date: " + e.getMessage());
                        }
                    }
                }
            }

            // Read past appointments into the attended list
            try (BufferedReader pastAppointmentsReader = new BufferedReader(new FileReader(studentPastAppointmentFile))) {
                String pastAppointmentLine;
                while ((pastAppointmentLine = pastAppointmentsReader.readLine()) != null) {
                    if (pastAppointmentLine.trim().isEmpty()) {
                        continue; // Skip empty lines
                    }

                    String[] pastAppointmentDetails = pastAppointmentLine.split(",");
                    // Check if this past appointment belongs to the current student
                    if (pastAppointmentDetails[0].equals(studentID)) {
                        String day = pastAppointmentDetails[1];
                        String time = pastAppointmentDetails[2];
                        String lecturer = pastAppointmentDetails[3];

                        try {
                            // Create date object for sorting
                            String startTime = time.split(" - ")[0].trim();
                            Date appointmentDate = sdf.parse(day + "," + startTime);

                            Object[] rowData = {
                                day,      // Day
                                time,     // Time
                                lecturer, // Lecturer
                                appointmentDate // For sorting
                            };
                            attendedList.add(rowData);
                        } catch (ParseException e) {
                            System.out.println("Error parsing date: " + e.getMessage());
                        }
                    }
                }
            }

            // Sort both lists by date and time
            Comparator<Object[]> dateComparator = new Comparator<Object[]>() {
                @Override
                public int compare(Object[] row1, Object[] row2) {
                    Date date1 = (Date) row1[3];
                    Date date2 = (Date) row2[3];
                    return date1.compareTo(date2);
                }
            };

            Collections.sort(upcomingList, dateComparator);
            Collections.sort(attendedList, dateComparator);

            // Add sorted upcoming appointments to table
            for (Object[] row : upcomingList) {
                upcomingAppointmentModel.addRow(new Object[]{row[0], row[1], row[2]});
            }

            // Add sorted attended appointments to table
            for (Object[] row : attendedList) {
                attendedAppointmentModel.addRow(new Object[]{row[0], row[1], row[2]});
            }

            // Rewrite studentAppointment.txt with only future appointments
            try (FileWriter fw = new FileWriter(studentAppointmentFile);
                 BufferedWriter bw = new BufferedWriter(fw)) {
                for (Object[] row : upcomingList) {
                    bw.write(studentID + "," + row[0] + "," + row[1] + "," + row[2] + "\n");
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jFrame2 = new javax.swing.JFrame();
        jLabel2 = new javax.swing.JLabel();
        attendedAppointment = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        cancelAppointmentButton1 = new javax.swing.JButton();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        upcomingAppointmentTable = new javax.swing.JTable();
        cancelAppointmentButton = new javax.swing.JButton();
        rescheduleAppointmentButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        attendedAppointmentTable = new javax.swing.JTable();
        ratePastAppointmentButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jLabel2.setText("jLabel2");

        attendedAppointment.setFont(new java.awt.Font("Modern No. 20", 1, 36)); // NOI18N
        attendedAppointment.setText("Attended Appointment");

        jLabel1.setFont(new java.awt.Font("Modern No. 20", 1, 36)); // NOI18N
        jLabel1.setText("Upcoming Appointment");

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 904, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        cancelAppointmentButton1.setText("Cancel");
        cancelAppointmentButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelAppointmentButton1ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLayeredPane1.setBackground(new java.awt.Color(204, 204, 204));

        jPanel3.setBackground(new java.awt.Color(204, 255, 255));

        jLabel3.setFont(new java.awt.Font("Modern No. 20", 1, 36)); // NOI18N
        jLabel3.setText("Upcoming Appointment");

        upcomingAppointmentTable.setFont(new java.awt.Font("Modern No. 20", 0, 12)); // NOI18N
        upcomingAppointmentTable.setModel(upcomingAppointmentModel);
        upcomingAppointmentTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                upcomingAppointmentTableMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(upcomingAppointmentTable);

        cancelAppointmentButton.setFont(new java.awt.Font("Modern No. 20", 0, 12)); // NOI18N
        cancelAppointmentButton.setText("Cancel");
        cancelAppointmentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelAppointmentButtonActionPerformed(evt);
            }
        });

        rescheduleAppointmentButton.setFont(new java.awt.Font("Modern No. 20", 0, 12)); // NOI18N
        rescheduleAppointmentButton.setText("Reschedule");
        rescheduleAppointmentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rescheduleAppointmentButtonActionPerformed(evt);
            }
        });

        backButton.setBackground(new java.awt.Color(153, 153, 153));
        backButton.setFont(new java.awt.Font("Modern No. 20", 1, 12)); // NOI18N
        backButton.setForeground(new java.awt.Color(255, 255, 255));
        backButton.setText("< Back");
        backButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(106, 106, 106)
                        .addComponent(rescheduleAppointmentButton)
                        .addGap(50, 50, 50)
                        .addComponent(cancelAppointmentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backButton)
                .addGap(11, 11, 11)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rescheduleAppointmentButton)
                    .addComponent(cancelAppointmentButton))
                .addContainerGap(433, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Modern No. 20", 1, 36)); // NOI18N
        jLabel4.setText("Attended Appointment");

        attendedAppointmentTable.setFont(new java.awt.Font("Modern No. 20", 0, 12)); // NOI18N
        attendedAppointmentTable.setModel(attendedAppointmentModel);
        attendedAppointmentTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                attendedAppointmentTableMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(attendedAppointmentTable);

        ratePastAppointmentButton.setFont(new java.awt.Font("Modern No. 20", 0, 12)); // NOI18N
        ratePastAppointmentButton.setText("Rate");
        ratePastAppointmentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ratePastAppointmentButtonActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(255, 0, 51));
        jButton1.setFont(new java.awt.Font("Modern No. 20", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Log out");
        jButton1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLayeredPane1.setLayer(jPanel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jScrollPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(ratePastAppointmentButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jButton1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ratePastAppointmentButton)
                        .addGap(185, 185, 185))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addContainerGap(16, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addGap(8, 8, 8)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ratePastAppointmentButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLayeredPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
   
    private void upcomingAppointmentTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_upcomingAppointmentTableMouseReleased
        row = upcomingAppointmentTable.getSelectedRow();
    }//GEN-LAST:event_upcomingAppointmentTableMouseReleased

    private void attendedAppointmentTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_attendedAppointmentTableMouseReleased
        row = attendedAppointmentTable.getSelectedRow();
    }//GEN-LAST:event_attendedAppointmentTableMouseReleased

    private void cancelAppointmentButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int row = upcomingAppointmentTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an appointment to cancel!");
            return;
        }

        String day = (String) upcomingAppointmentTable.getValueAt(row, 0);
        String time = (String) upcomingAppointmentTable.getValueAt(row, 1);
        String lecturer = (String) upcomingAppointmentTable.getValueAt(row, 2);

        // Format for studentAppointmentFile
        String appointmentToRemove = studentID + "," + day + "," + time + "," + lecturer;
        // Format for lecSlotsFile
        String slotToAdd = day + "," + time + "," + lecturer;

        try {
            // First check if there's a pending reschedule request
            boolean hasPendingReschedule = false;
            List<String> remainingReschedules = new ArrayList<>();
            String newSlotToAdd = null;
            
            try (Scanner sc = new Scanner(studentRescheduleFile)) {
                while (sc.hasNextLine()) {
                    String line = sc.nextLine().trim();
                    if (!line.isEmpty()) {
                        String[] parts = line.split(",");
                        if (parts.length == 8 && parts[0].equals(studentID) &&
                            parts[1].equals(day) && parts[2].equals(time) && 
                            parts[3].equals(lecturer) && parts[7].equals("Pending")) {
                            hasPendingReschedule = true;
                            newSlotToAdd = parts[4] + "," + parts[5] + "," + parts[6];
                        } else {
                            remainingReschedules.add(line);
                        }
                    }
                }
            }

            // Remove from studentAppointmentFile
            List<String> remainingAppointments = new ArrayList<>();
            try (Scanner sc = new Scanner(studentAppointmentFile)) {
                while (sc.hasNextLine()) {
                    String line = sc.nextLine().trim();
                    if (!line.equals(appointmentToRemove)) {
                        remainingAppointments.add(line);
                    }
                }
            }

            // Write back to studentAppointmentFile
            try (FileWriter fw = new FileWriter(studentAppointmentFile);
                 BufferedWriter bw = new BufferedWriter(fw)) {
                for (String appointment : remainingAppointments) {
                    bw.write(appointment + "\n");
                }
            }
            
            // Add the appropriate slot back to lecSlotsFile
            try (FileWriter fw = new FileWriter(lecSlotsFile, true);
                 BufferedWriter bw = new BufferedWriter(fw)) {
                if (hasPendingReschedule) {
                    // Add both the original and new slots back
                    bw.write(slotToAdd + "\n");
                    bw.write(newSlotToAdd + "\n");
                } else {
                    // Add only the original slot back
                    bw.write(slotToAdd + "\n");
                }
            }

            // Update studentRescheduleFile if needed
            if (hasPendingReschedule) {
                try (FileWriter fw = new FileWriter(studentRescheduleFile);
                     BufferedWriter bw = new BufferedWriter(fw)) {
                    for (String reschedule : remainingReschedules) {
                        bw.write(reschedule + "\n");
                    }
                }
            }

            // Remove from table
            upcomingAppointmentModel.removeRow(row);
            row = -1;

            JOptionPane.showMessageDialog(this, "Appointment cancelled successfully!");

            // Refresh the consultation slots UI
            StudentConsultationSlots scs = new StudentConsultationSlots(studentID);
            scs.setVisible(true);
            this.dispose();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void rescheduleAppointmentButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int row = upcomingAppointmentTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an appointment to reschedule!");
            return;
        }

        // Get the details of the selected appointment
        String oldDay = (String) upcomingAppointmentTable.getValueAt(row, 0);
        String oldTime = (String) upcomingAppointmentTable.getValueAt(row, 1);
        String oldLecturer = (String) upcomingAppointmentTable.getValueAt(row, 2);

        // First check if there's already a pending reschedule request for this appointment
        try {
            // Create a set to store all existing time slots for this student
            Set<String> existingSlots = new HashSet<>();
            
            // Check appointments in studentAppointment.txt
            try (BufferedReader br = new BufferedReader(new FileReader(studentAppointmentFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    
                    String[] parts = line.split(",");
                    if (parts.length >= 4 && parts[0].equals(studentID)) {
                        // Skip the appointment being rescheduled
                        if (!parts[1].equals(oldDay) || !parts[2].equals(oldTime) || !parts[3].equals(oldLecturer)) {
                            // Store day and time combination
                            existingSlots.add(parts[1] + "," + parts[2]);
                        }
                    }
                }
            }

            // Check pending reschedule requests
            try (BufferedReader br = new BufferedReader(new FileReader(studentRescheduleFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    
                    String[] parts = line.split(",");
                    if (parts.length == 8 && parts[0].equals(studentID)) {
                        // Check if this is the same appointment
                        if (parts[1].equals(oldDay) && parts[2].equals(oldTime) && parts[3].equals(oldLecturer)) {
                            // Check if the request is still pending
                            if (parts[7].trim().equals("Pending")) {
                                JOptionPane.showMessageDialog(this, 
                                    "You already have a pending reschedule request for this appointment.\n" +
                                    "Please wait for the lecturer's response.");
                                return;
                            }
                        }
                        
                        // Add new slot from pending requests to existing slots
                        if (parts[7].trim().equals("Pending")) {
                            existingSlots.add(parts[4] + "," + parts[5]);
                        }
                    }
                }
            }

            // Store the conflict information and existing slots for later use
            File tempFile = new File("temp_reschedule.txt");
            try (FileWriter fw = new FileWriter(tempFile);
                 BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(oldDay + "," + oldTime + "," + oldLecturer + "\n");
                // Store existing slots to check against
                for (String slot : existingSlots) {
                    bw.write("EXISTING:" + slot + "\n");
                }
            }

            // Redirect to consultation slots page
            StudentConsultationSlots scs = new StudentConsultationSlots(studentID);
            scs.setVisible(true);
            this.dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void ratePastAppointmentButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int row = attendedAppointmentTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an appointment to rate!");
            return;
        }

        // Get the details of the selected past appointment
        String day = (String) attendedAppointmentTable.getValueAt(row, 0);
        String time = (String) attendedAppointmentTable.getValueAt(row, 1);
        String lecturer = (String) attendedAppointmentTable.getValueAt(row, 2);

        // Check if this appointment has already been rated
        try (BufferedReader br = new BufferedReader(new FileReader("studentFeedback.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && 
                    parts[0].equals(studentID) && 
                    parts[1].equals(day) && 
                    parts[2].equals(time) && 
                    parts[3].equals(lecturer)) {
                    JOptionPane.showMessageDialog(this, "You have already rated this appointment!");
                    return;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error checking feedback: " + e.getMessage());
            return;
        }

        // If not already rated, proceed to feedback page
        StudentFeedback fp = new StudentFeedback(studentID, day, time, lecturer);
        fp.setVisible(true);
        this.dispose();
    }

    private void cancelAppointmentButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelAppointmentButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cancelAppointmentButton1ActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        StudentDashboard sd = new StudentDashboard(studentID);
        sd.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_backButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        StudentLogin sl = new StudentLogin();
        sl.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StudentAppointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentAppointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentAppointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentAppointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new StudentAppointment().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel attendedAppointment;
    private javax.swing.JTable attendedAppointmentTable;
    private javax.swing.JButton backButton;
    private javax.swing.JButton cancelAppointmentButton;
    private javax.swing.JButton cancelAppointmentButton1;
    private javax.swing.JButton jButton1;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton ratePastAppointmentButton;
    private javax.swing.JButton rescheduleAppointmentButton;
    private javax.swing.JTable upcomingAppointmentTable;
    // End of variables declaration//GEN-END:variables
}
