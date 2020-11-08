package br.gov.sp.fatec.backend.controllers;

import br.gov.sp.fatec.backend.models.Member;
import br.gov.sp.fatec.backend.services.MemberService;
import br.gov.sp.fatec.backend.views.Views;

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("API REST Gruly Member")
@RequestMapping("/api/members")
@CrossOrigin(origins = "*")
public class MemberController {
  @Autowired
  MemberService memberService;

  @ApiOperation(value = "Retorna uma lista com os dados de todos os membros")
  @JsonView(Views.SummaryMemberView.class)
  @GetMapping
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<List<Member>> getAllMembers() {
    return ResponseEntity.ok(memberService.getAllMembers());
  }

  @JsonView(Views.DetailMemberView.class)
  @GetMapping("/{memberId}")
  @PreAuthorize("isAuthenticated()")
  @ApiOperation(value = "Retorna os dados de um membro")
  public ResponseEntity<Member> getMemberById(@PathVariable("memberId") long memberId) {    
    return ResponseEntity.ok(memberService.getMemberById(memberId));
  }

  @PostMapping
  @ApiOperation(value = "Insere os dados de um membro")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<Member> createMember(@RequestBody Member member) {
    memberService.createMember(member);

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("/{memberId}")
  @ApiOperation(value = "Atualiza os dados de um membro")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<Member> updateMemberById(@PathVariable("memberId") long memberId,
  @RequestBody Member memberDataToUpdate) {
    memberService.updateMemberById(memberId, memberDataToUpdate);

    return ResponseEntity.ok().build();
  }
  
  @PutMapping("/{memberId}/role")
  @ApiOperation(value = "Atualiza a permissão de um membro")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<Member> updateMemberRole(@PathVariable("memberId") long memberId,
  @RequestParam("roleId") long memberRoleId) {
    memberService.updateMemberRole(memberId, memberRoleId);
    
    return ResponseEntity.ok().build();
  }
  
  @DeleteMapping("/{memberId}")
  @ApiOperation(value = "Deleta os dados de um membro")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<Member> deleteMemberById(@PathVariable("memberId") long memberId) {
    memberService.deleteMemberById(memberId);

    return ResponseEntity.ok().build();
  }
}