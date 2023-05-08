package hw4.hw4.Repository;

import hw4.hw4.Entity.Config.WebsiteSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebsiteSettingsRepository extends JpaRepository<WebsiteSettings, Long> {}
