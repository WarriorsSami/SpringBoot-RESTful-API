package com.sami.jwtauthapi

import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

class WebCofig: WebMvcConfigurer {

    public override fun addCorsMappings(registry: CorsRegistry) {
        
    }
}