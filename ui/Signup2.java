package ui;

import javax.swing.*;

public class Signup2 {

    public Signup2(String formNo, String name, String father, String gender,
                   String email, String marital, String address,
                   String city, String pin, String state) {

        JFrame frame = new JFrame("Form 2");
        frame.setSize(500,500);
        frame.setLayout(null);

        // Labels
        JLabel religionL = new JLabel("Religion");
        JLabel categoryL = new JLabel("Category");
        JLabel incomeL = new JLabel("Income");
        JLabel educationL = new JLabel("Education");
        JLabel occupationL = new JLabel("Occupation");
        JLabel cnicL = new JLabel("CNIC");
        JLabel seniorL = new JLabel("Senior Citizen");

        // Components
        JComboBox religion = new JComboBox(new String[]{"Islam","Christian","Hindu"});
        JComboBox category = new JComboBox(new String[]{"General","OBC","SC","ST"});
        JComboBox income = new JComboBox(new String[]{"Null","<50k","<1L","1L+"});
        JComboBox education = new JComboBox(new String[]{"Non-Graduate","Graduate","Post-Graduate"});
        JComboBox occupation = new JComboBox(new String[]{"Student","Salaried","Business"});

        JTextField cnic = new JTextField();

        JRadioButton yes = new JRadioButton("Yes");
        JRadioButton no = new JRadioButton("No");

        ButtonGroup seniorGroup = new ButtonGroup();
        seniorGroup.add(yes);
        seniorGroup.add(no);

        JButton next = new JButton("Next");

        // Label Positions
        religionL.setBounds(50,50,150,25);
        categoryL.setBounds(50,90,150,25);
        incomeL.setBounds(50,130,150,25);
        educationL.setBounds(50,170,150,25);
        occupationL.setBounds(50,210,150,25);
        cnicL.setBounds(50,250,150,25);
        seniorL.setBounds(50,290,150,25);

        // Component Positions
        religion.setBounds(200,50,200,25);
        category.setBounds(200,90,200,25);
        income.setBounds(200,130,200,25);
        education.setBounds(200,170,200,25);
        occupation.setBounds(200,210,200,25);
        cnic.setBounds(200,250,200,25);
        yes.setBounds(200,290,80,25);
        no.setBounds(280,290,80,25);
        next.setBounds(200,350,100,30);

        // Add to frame
        frame.add(religionL); frame.add(categoryL); frame.add(incomeL);
        frame.add(educationL); frame.add(occupationL);
        frame.add(cnicL); frame.add(seniorL);

        frame.add(religion); frame.add(category); frame.add(income);
        frame.add(education); frame.add(occupation);
        frame.add(cnic); frame.add(yes); frame.add(no); frame.add(next);

        // Button Action
        next.addActionListener(e -> {
            frame.dispose();

            new Signup3(
                formNo, name, father, gender, email, marital,
                address, city, pin, state,
                religion.getSelectedItem().toString(),
                category.getSelectedItem().toString(),
                income.getSelectedItem().toString(),
                education.getSelectedItem().toString(),
                occupation.getSelectedItem().toString(),
                cnic.getText(),
                yes.isSelected() ? "Yes" : "No",
                "No"
            );
        });

        frame.setVisible(true);
    }
}