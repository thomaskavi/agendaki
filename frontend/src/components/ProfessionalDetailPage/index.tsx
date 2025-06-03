// src/components/ProfessionalDetailPage/index.tsx
import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom'; // Importe useNavigate
import { getProfessionalBySlug, type ProfessionalPublicDTO, type ServiceOfferedDTO, getErrorMessage } from '../../services/api';
import { useAuth } from '../../hooks/useAuth'; // Importe useAuth para verificar autenticação
import styles from './styles.module.css';

const defaultProfileImage = 'src/assets/default-profile-image.jpg'; // Mantido seu caminho

function ProfessionalDetailPage() {
  const { slug } = useParams<{ slug: string }>();
  const navigate = useNavigate(); // Hook para navegação
  const { isAuthenticated } = useAuth(); // Hook para verificar autenticação

  const [professional, setProfessional] = useState<ProfessionalPublicDTO | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (!slug) {
      setError("Slug do profissional não encontrado.");
      setIsLoading(false);
      return;
    }

    async function fetchProfessionalDetails() {
      setIsLoading(true);
      setError(null);
      try {
        const response = await getProfessionalBySlug(slug);
        setProfessional(response.data);
      } catch (err) {
        console.error(`Erro ao buscar detalhes do profissional ${slug}:`, err);
        setError(getErrorMessage(err));
      } finally {
        setIsLoading(false);
      }
    }
    fetchProfessionalDetails();
  }, [slug]);

  const handleScheduleService = (serviceId: bigint, professionalId: bigint) => {
    if (!isAuthenticated) {
      // Redireciona para o login, guardando a URL atual para voltar depois
      navigate('/login', { state: { from: window.location.pathname } });
    } else {
      // Se autenticado, navega para a página de agendamento
      // Os IDs são BigInt, mas serão passados como strings na URL
      navigate(`/agendar/${professionalId.toString()}/${serviceId.toString()}`);
    }
  };

  if (isLoading) {
    return <div className={styles.statusMessage}>Carregando perfil...</div>;
  }

  if (error) {
    return <div className={`${styles.statusMessage} ${styles.error}`}>Erro: {error}</div>;
  }

  if (!professional) {
    return <div className={styles.statusMessage}>Perfil do profissional não encontrado.</div>;
  }

  return (
    <main className={styles.detailContainer}>
      <div className={styles.profileHeader}>
        <img
          src={professional.profileImageUrl || defaultProfileImage}
          alt={`Foto de ${professional.name}`}
          className={styles.profileImage}
          onError={(e) => (e.currentTarget.src = defaultProfileImage)}
        />
        <div className={styles.headerInfo}>
          <h1 className={styles.name}>{professional.name}</h1>
          <p className={styles.profession}>{professional.profession}</p>
          <p className={styles.contactInfo}>
            <strong>Email:</strong> <a href={`mailto:${professional.email}`}>{professional.email}</a>
          </p>
          <p className={styles.contactInfo}>
            <strong>Telefone:</strong> <a href={`tel:${professional.phone}`}>{professional.phone}</a>
          </p>
          <p className={styles.slugInfo}>
            <strong>Link Direto:</strong> <span>/agdk/{professional.slug}</span>
          </p>
        </div>
      </div>

      <section className={styles.servicesSection}>
        <h2 className={styles.sectionTitle}>Serviços Oferecidos</h2>
        {professional.services && professional.services.length > 0 ? (
          <ul className={styles.servicesList}>
            {professional.services.map((service: ServiceOfferedDTO) => (
              <li key={String(service.id)} className={styles.serviceItem}> {/* Convertendo bigint para string para a key */}
                <h3 className={styles.serviceName}>{service.name}</h3>
                <p className={styles.serviceDescription}>{service.description}</p>
                <p className={styles.servicePrice}>
                  Preço: R$ {service.price.toFixed(2).replace('.', ',')}
                </p>
                <button
                  className={styles.scheduleButton}
                  onClick={() => handleScheduleService(service.id, professional.id)}
                >
                  Agendar este serviço
                </button>
              </li>
            ))}
          </ul>
        ) : (
          <p>Nenhum serviço cadastrado no momento.</p>
        )}
      </section>
      {/* Outras seções: Agenda, Avaliações, etc. */}
    </main>
  );
}

export default ProfessionalDetailPage;