package com.gijun.erpproject.Controller;

import com.gijun.erpproject.Response.PageResponseDTO;
import com.gijun.erpproject.Response.TeamResponseDTO;
import com.gijun.erpproject.Service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@Tag(name = "Team Management", description = "팀 관리 API")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @Operation(
            summary = "전체 팀 계층구조 조회",
            description = "회사의 전체 팀 계층구조와 팀원 정보를 페이징하여 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PageResponseDTO.class)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<PageResponseDTO<TeamResponseDTO>> getAllTeams(
            @RequestParam(defaultValue = "0") @Parameter(description = "페이지 번호") int page,
            @RequestParam(defaultValue = "10") @Parameter(description = "페이지 크기") int size) {
        return ResponseEntity.ok(teamService.getAllTeamsHierarchy(page, size));
    }
}