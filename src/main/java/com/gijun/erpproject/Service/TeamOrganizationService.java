package com.gijun.erpproject.Service;

import com.gijun.erpproject.Repository.TeamOrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamOrganizationService {

    private final TeamOrganizationRepository teamOrganizationRepository;

}
