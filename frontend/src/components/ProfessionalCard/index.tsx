// src/components/ProfessionalCard/index.tsx
import { Link } from 'react-router-dom';
import { type Professional } from '../../services/api'; // Usando o tipo Professional
import styles from './styles.module.css';
// import defaultProfileImage from '../../assets/default-profile.png'; // Imagem padrão

interface ProfessionalCardProps {
  professional: Professional;
}

// Você precisará de uma imagem padrão em `src/assets/default-profile.png`
// ou ajuste o caminho/lógica.
const defaultProfileImage = 'src/assets/default-profile-image.jpg';


function ProfessionalCard({ professional }: ProfessionalCardProps) {
  return (
    <article className={styles.card}>
      <img
        src={professional.profileImageUrl || defaultProfileImage}
        alt={`Foto de ${professional.name}`}
        className={styles.profileImage}
        onError={(e) => (e.currentTarget.src = defaultProfileImage)} // Fallback se a imagem falhar
      />
      <div className={styles.cardContent}>
        <h3 className={styles.name}>{professional.name}</h3>
        <p className={styles.profession}>{professional.profession}</p>
        {professional.phone && (
          <p className={styles.contact}>
            Contato: <a href={`tel:${professional.phone}`}>{professional.phone}</a>
          </p>
        )}
        <Link to={`/agdk/${professional.slug}`} className={styles.profileLink}>
          Ver Perfil e Agendar
        </Link>
      </div>
    </article>
  );
}

export default ProfessionalCard;