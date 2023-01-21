package br.com.zal.oauth2resourceserver.repository;

import br.com.zal.oauth2resourceserver.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrayerRepository extends JpaRepository<Player, String> {


}
