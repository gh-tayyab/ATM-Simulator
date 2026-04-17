package ui;

import javax.swing.*;
import java.awt.event.*;

public class Signup1 {

    JFrame frame;
    JTextField name, father, email, address, city, pin, state;
    JRadioButton male, female, married, unmarried;
    String formNo;

    public Signup1() {

        formNo = "" + (int)(Math.random() * 9000 + 1000);

        frame = new JFrame("Application Form No. " + formNo);
        frame.setSize(500, 600);
        frame.setLayout(null);

        JLabel title = new JLabel("APPLICATION FORM NO. " + formNo);
        title.setBounds(100, 20, 300, 30);
        frame.add(title);

        // Text Fields
        name = new JTextField(); name.setBounds(200, 80, 200, 25);
        father = new JTextField(); father.setBounds(200, 120, 200, 25);
        email = new JTextField(); email.setBounds(200, 200, 200, 25);
        address = new JTextField(); address.setBounds(200, 240, 200, 25);
        city = new JTextField(); city.setBounds(200, 280, 200, 25);
        pin = new JTextField(); pin.setBounds(200, 320, 200, 25);
        state = new JTextField(); state.setBounds(200, 360, 200, 25);

        // Gender Radio Buttons
        male = new JRadioButton("Male");
        female = new JRadioButton("Female");
        male.setBounds(200,160,80,25);
        female.setBounds(280,160,80,25);

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);

        // Marital Status Radio Buttons
        married = new JRadioButton("Married");
        unmarried = new JRadioButton("Unmarried");
        married.setBounds(200, 400, 100, 25);
        unmarried.setBounds(300, 400, 100, 25);

        ButtonGroup maritalGroup = new ButtonGroup();
        maritalGroup.add(married);
        maritalGroup.add(unmarried);

        // Button
        JButton next = new JButton("Next");
        next.setBounds(200, 450, 100, 30);

        // Labels + Add Components
        frame.add(new JLabel("Name")).setBounds(50,80,150,25);
        frame.add(name);

        frame.add(new JLabel("Father Name")).setBounds(50,120,150,25);
        frame.add(father);

        frame.add(new JLabel("Gender")).setBounds(50,160,150,25);
        frame.add(male); 
        frame.add(female);

        frame.add(new JLabel("Email")).setBounds(50,200,150,25);
        frame.add(email);

        frame.add(new JLabel("Address")).setBounds(50,240,150,25);
        frame.add(address);

        frame.add(new JLabel("City")).setBounds(50,280,150,25);
        frame.add(city);

        frame.add(new JLabel("Postal Code")).setBounds(50,320,150,25);
        frame.add(pin);

        frame.add(new JLabel("State")).setBounds(50,360,150,25);
        frame.add(state);

        frame.add(new JLabel("Marital Status")).setBounds(50,400,150,25);
        frame.add(married); 
        frame.add(unmarried);

        frame.add(next);

        // ✅ Updated ActionListener
        next.addActionListener(e -> {

            String gender = male.isSelected() ? "Male" : "Female";
            String marital = married.isSelected() ? "Married" : "Unmarried";

            frame.dispose();

            new Signup2(formNo,
                name.getText(),
                father.getText(),
                gender,
                email.getText(),
                marital,
                address.getText(),
                city.getText(),
                pin.getText(),
                state.getText()
            );
        });

        frame.setVisible(true);
    }
}