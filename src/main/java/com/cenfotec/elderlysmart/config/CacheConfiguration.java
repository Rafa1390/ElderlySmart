package com.cenfotec.elderlysmart.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.cenfotec.elderlysmart.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.cenfotec.elderlysmart.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.cenfotec.elderlysmart.domain.User.class.getName());
            createCache(cm, com.cenfotec.elderlysmart.domain.Authority.class.getName());
            createCache(cm, com.cenfotec.elderlysmart.domain.User.class.getName() + ".authorities");
            createCache(cm, com.cenfotec.elderlysmart.domain.PersistentToken.class.getName());
            createCache(cm, com.cenfotec.elderlysmart.domain.User.class.getName() + ".persistentTokens");
            createCache(cm, com.cenfotec.elderlysmart.domain.UserApp.class.getName());
            createCache(cm, com.cenfotec.elderlysmart.domain.Asylum.class.getName());
            createCache(cm, com.cenfotec.elderlysmart.domain.Asylum.class.getName() + ".cleaningPrograms");
            createCache(cm, com.cenfotec.elderlysmart.domain.Asylum.class.getName() + ".foodMenus");
            createCache(cm, com.cenfotec.elderlysmart.domain.Asylum.class.getName() + ".recreationalActivities");
            createCache(cm, com.cenfotec.elderlysmart.domain.Asylum.class.getName() + ".employees");
            createCache(cm, com.cenfotec.elderlysmart.domain.Asylum.class.getName() + ".mortuaries");
            createCache(cm, com.cenfotec.elderlysmart.domain.Asylum.class.getName() + ".pharmacies");
            createCache(cm, com.cenfotec.elderlysmart.domain.Asylum.class.getName() + ".partners");
            createCache(cm, com.cenfotec.elderlysmart.domain.CleaningProgram.class.getName());
            createCache(cm, com.cenfotec.elderlysmart.domain.Employee.class.getName());
            createCache(cm, com.cenfotec.elderlysmart.domain.Employee.class.getName() + ".elderlies");
            createCache(cm, com.cenfotec.elderlysmart.domain.Employee.class.getName() + ".medicalAppointments");
            createCache(cm, com.cenfotec.elderlysmart.domain.FoodMenu.class.getName());
            createCache(cm, com.cenfotec.elderlysmart.domain.Medicament.class.getName());
            createCache(cm, com.cenfotec.elderlysmart.domain.Product.class.getName());
            createCache(cm, com.cenfotec.elderlysmart.domain.Product.class.getName() + ".inventoryProviders");
            createCache(cm, com.cenfotec.elderlysmart.domain.ProviderHistory.class.getName());
            createCache(cm, com.cenfotec.elderlysmart.domain.Allergies.class.getName());
            createCache(cm, com.cenfotec.elderlysmart.domain.CaseFile.class.getName());
            createCache(cm, com.cenfotec.elderlysmart.domain.CaseFile.class.getName() + ".alergies");
            createCache(cm, com.cenfotec.elderlysmart.domain.CaseFile.class.getName() + ".pathologies");
            createCache(cm, com.cenfotec.elderlysmart.domain.Elderly.class.getName());
            createCache(cm, com.cenfotec.elderlysmart.domain.Elderly.class.getName() + ".families");
            createCache(cm, com.cenfotec.elderlysmart.domain.Elderly.class.getName() + ".doctors");
            createCache(cm, com.cenfotec.elderlysmart.domain.InventoryPharmacy.class.getName());
            createCache(cm, com.cenfotec.elderlysmart.domain.InventoryProvider.class.getName());
            createCache(cm, com.cenfotec.elderlysmart.domain.InventoryProvider.class.getName() + ".products");
            createCache(cm, com.cenfotec.elderlysmart.domain.Pathologies.class.getName());
            createCache(cm, com.cenfotec.elderlysmart.domain.Provider.class.getName());
            createCache(cm, com.cenfotec.elderlysmart.domain.Provider.class.getName() + ".pharmacies");
            createCache(cm, com.cenfotec.elderlysmart.domain.RecreationalActivity.class.getName());
            createCache(cm, com.cenfotec.elderlysmart.domain.Pharmacy.class.getName());
            createCache(cm, com.cenfotec.elderlysmart.domain.Pharmacy.class.getName() + ".prescriptions");
            createCache(cm, com.cenfotec.elderlysmart.domain.Pharmacy.class.getName() + ".providers");
            createCache(cm, com.cenfotec.elderlysmart.domain.Pharmacy.class.getName() + ".asylums");
            createCache(cm, com.cenfotec.elderlysmart.domain.Doctor.class.getName());
            createCache(cm, com.cenfotec.elderlysmart.domain.Doctor.class.getName() + ".prescriptions");
            createCache(cm, com.cenfotec.elderlysmart.domain.Doctor.class.getName() + ".medicalAppointments");
            createCache(cm, com.cenfotec.elderlysmart.domain.Doctor.class.getName() + ".elderlies");
            createCache(cm, com.cenfotec.elderlysmart.domain.Partner.class.getName());
            createCache(cm, com.cenfotec.elderlysmart.domain.Partner.class.getName() + ".asylums");
            createCache(cm, com.cenfotec.elderlysmart.domain.Family.class.getName());
            createCache(cm, com.cenfotec.elderlysmart.domain.Family.class.getName() + ".elderlies");
            createCache(cm, com.cenfotec.elderlysmart.domain.Mortuary.class.getName());
            createCache(cm, com.cenfotec.elderlysmart.domain.Mortuary.class.getName() + ".funeralPackages");
            createCache(cm, com.cenfotec.elderlysmart.domain.Mortuary.class.getName() + ".asylums");
            createCache(cm, com.cenfotec.elderlysmart.domain.FuneralHistory.class.getName());
            createCache(cm, com.cenfotec.elderlysmart.domain.FuneralPackages.class.getName());
            createCache(cm, com.cenfotec.elderlysmart.domain.DonationHistory.class.getName());
            createCache(cm, com.cenfotec.elderlysmart.domain.PharmacyHistory.class.getName());
            createCache(cm, com.cenfotec.elderlysmart.domain.Prescription.class.getName());
            createCache(cm, com.cenfotec.elderlysmart.domain.PrescriptionNotification.class.getName());
            createCache(cm, com.cenfotec.elderlysmart.domain.MedicationSchedule.class.getName());
            createCache(cm, com.cenfotec.elderlysmart.domain.MedicalAppointment.class.getName());
            createCache(cm, com.cenfotec.elderlysmart.domain.AppointmentNotification.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

}
