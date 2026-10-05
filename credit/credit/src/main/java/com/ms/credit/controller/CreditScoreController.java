package com.ms.credit.controller;

import org.springframework.web.bind.annotation.*;
import com.ms.credit.dto.CreditScoreDTO;
import com.ms.credit.dto.ScoreHistoryDTO;
import com.ms.credit.service.CreditScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;


@RestController
@RequestMapping("/score")
public class CreditScoreController {

    @Autowired
    private CreditScoreService creditScoreService;

    @GetMapping("/{userId}")
    public CreditScoreDTO getCreditScore(@PathVariable int userId) {
        return creditScoreService.getCreditScoreByUserId(userId);
    }

    @PostMapping("/calculate")
    public CreditScoreDTO calculateCreditScore(@RequestBody CreditScoreDTO creditScoreDTO) {
        return creditScoreService.calculateCreditScore(creditScoreDTO);
    }

    @PutMapping("/{userId}")
    public CreditScoreDTO updateCreditScore(@PathVariable int userId, @RequestBody CreditScoreDTO creditScoreDTO) {
        return creditScoreService.updateCreditScore(userId, creditScoreDTO);
    }

    @DeleteMapping("/{userId}")
    public void deleteCreditScore(@PathVariable int userId) {
        creditScoreService.deleteCreditScoreByUserId(userId);
    }

    @GetMapping("/history/{userId}")
    public List<ScoreHistoryDTO> getCreditScoreHistory(@PathVariable int userId) {
        return creditScoreService.getCreditScoreHistoryByUserId(userId);
    }

    @PostMapping("/batch")
    public List<CreditScoreDTO> calculateBatchCreditScores(@RequestBody List<CreditScoreDTO> creditScores) {
        return creditScoreService.calculateBatchCreditScores(creditScores);
    }

    @GetMapping("/average")
    public double getAverageCreditScore() {
        return creditScoreService.getAverageCreditScore();
    }

    @PutMapping("/refresh")
    public void refreshCreditScores() {
        creditScoreService.refreshCreditScores();
    }
}
