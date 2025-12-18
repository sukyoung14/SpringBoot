package com.example.instagram.controller;

import com.example.instagram.security.CustomUserDetails;
import com.example.instagram.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping
    public ResponseEntity<Void> toggleBookmark(
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        bookmarkService.toggleBookmark(postId, userDetails.getId());
        return ResponseEntity.ok().build();
    }
}
