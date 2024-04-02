package farmApp.farmbackend.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import farmApp.farmbackend.entities.Goat;

public interface GoatRepository extends JpaRepository<Goat, Long> {

	
}
