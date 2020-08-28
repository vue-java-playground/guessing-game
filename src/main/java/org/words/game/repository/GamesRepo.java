package org.words.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.words.game.repository.entity.GameInstace;


@Repository
public interface GamesRepo extends JpaRepository<GameInstace, Long> {

}
