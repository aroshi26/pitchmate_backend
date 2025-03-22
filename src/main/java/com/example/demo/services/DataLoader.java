package com.example.demo.services;

import com.example.demo.Entities.Enum.PlayerRoleType;
import com.example.demo.Entities.PlayerRole;
import com.example.demo.Entities.Role;
import com.example.demo.Entities.Specialization;
import com.example.demo.Repositories.PlayerRoleRepository;
import com.example.demo.Repositories.RoleRepository;
import com.example.demo.Repositories.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SpecializationRepository specializationRepository;

    @Autowired
    private PlayerRoleRepository playerRoleRepository;

    @Override
    public void run(String... args) throws Exception {
        insertRoles();
        insertSpecializations();
        insertPlayerRoles();
    }

    private void insertRoles() {
        if (roleRepository.count() == 0) {
            Role coach = new Role();
            coach.setRoleId(1);
            coach.setRoleName("Coach");

            Role medicalOfficer = new Role();
            medicalOfficer.setRoleId(2);
            medicalOfficer.setRoleName("MedicalOfficer");

            Role admin = new Role();
            admin.setRoleId(3);
            admin.setRoleName("Admin");

            roleRepository.saveAll(Arrays.asList(coach, medicalOfficer, admin));
            System.out.println("✅ Roles inserted successfully!");
        } else {
            System.out.println("✅ Roles already exist.");
        }
    }

    private void insertSpecializations() {
        if (specializationRepository.count() == 0) {
            Role coach = roleRepository.findByRoleName("Coach");
            Role medicalOfficer = roleRepository.findByRoleName("MedicalOfficer");
            Role admin = roleRepository.findByRoleName("Admin");

            Specialization headCoach = new Specialization();
            headCoach.setSpecializationId(1);
            headCoach.setRole(coach);
            headCoach.setSpecializationName("Head Coach");

            Specialization bowlingCoach = new Specialization();
            bowlingCoach.setSpecializationId(2);
            bowlingCoach.setRole(coach);
            bowlingCoach.setSpecializationName("Bowling Coach");

            Specialization battingCoach = new Specialization();
            battingCoach.setSpecializationId(3);
            battingCoach.setRole(coach);
            battingCoach.setSpecializationName("Batting Coach");

            Specialization fieldingCoach = new Specialization();
            fieldingCoach.setSpecializationId(4);
            fieldingCoach.setRole(coach);
            fieldingCoach.setSpecializationName("Fielding Coach");

            Specialization doctor = new Specialization();
            doctor.setSpecializationId(5);
            doctor.setRole(medicalOfficer);
            doctor.setSpecializationName("Doctor");

            Specialization physiotherapist = new Specialization();
            physiotherapist.setSpecializationId(6);
            physiotherapist.setRole(medicalOfficer);
            physiotherapist.setSpecializationName("Physiotherapist");

            Specialization psychologist = new Specialization();
            psychologist.setSpecializationId(7);
            psychologist.setRole(medicalOfficer);
            psychologist.setSpecializationName("Psychologist");

            Specialization general = new Specialization();
            general.setSpecializationId(8);
            general.setRole(admin);
            general.setSpecializationName("General");

            specializationRepository.saveAll(Arrays.asList(
                    headCoach, bowlingCoach, battingCoach, fieldingCoach,
                    doctor, physiotherapist, psychologist, general));
            System.out.println("✅ Specializations inserted successfully!");
        } else {
            System.out.println("✅ Specializations already exist.");
        }
    }

    private void insertPlayerRoles() {
        if (playerRoleRepository.count() == 0) {
            PlayerRole batsman = new PlayerRole();
            batsman.setPlayerRoleId(1);
            batsman.setRoleName(PlayerRoleType.BATSMAN);

            PlayerRole bowler = new PlayerRole();
            bowler.setPlayerRoleId(2);
            bowler.setRoleName(PlayerRoleType.BOWLER);

            PlayerRole allRounder = new PlayerRole();
            allRounder.setPlayerRoleId(3);
            allRounder.setRoleName(PlayerRoleType.ALLROUNDER);

            PlayerRole wicketKeeper = new PlayerRole();
            wicketKeeper.setPlayerRoleId(4);
            wicketKeeper.setRoleName(PlayerRoleType.WICKETKEEPER);

            playerRoleRepository.saveAll(Arrays.asList(batsman, bowler, allRounder, wicketKeeper));
            System.out.println("✅ Player roles inserted successfully!");
        } else {
            System.out.println("✅ Player roles already exist.");
        }
    }
}
