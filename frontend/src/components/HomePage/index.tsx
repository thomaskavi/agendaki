// src/components/HomePage/index.tsx
import { useEffect, useState } from 'react';
import { getProfessionals, type Professional, getErrorMessage } from '../../services/api';
import ProfessionalCard from '../ProfessionalCard';
import styles from './styles.module.css';

function HomePage() {
  const [professionals, setProfessionals] = useState<Professional[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  // Para paginação, você adicionaria estados para page, totalPages, etc.
  // const [currentPage, setCurrentPage] = useState(0);

  useEffect(() => {
    async function fetchProfessionals() {
      setIsLoading(true);
      setError(null);
      try {
        // Usando os valores padrão para page, size, sort da função getProfessionals
        const response = await getProfessionals();
        // A API de exemplo retorna { content: Professional[] }
        setProfessionals(response.data.content || []); // Ajuste se a estrutura for diferente
      } catch (err) {
        console.error("Erro ao buscar profissionais:", err);
        setError(getErrorMessage(err));
      } finally {
        setIsLoading(false);
      }
    }
    fetchProfessionals();
  }, []); // Adicionar currentPage aqui se implementar paginação

  if (isLoading) {
    return <div className={styles.statusMessage}>Carregando profissionais...</div>;
  }

  if (error) {
    return <div className={`${styles.statusMessage} ${styles.error}`}>Erro ao carregar: {error}</div>;
  }

  if (professionals.length === 0) {
    return <div className={styles.statusMessage}>Nenhum profissional encontrado.</div>;
  }

  return (
    <main className={styles.homeContainer}>
      <h2 className={styles.title}>Profissionais Disponíveis</h2>
      <div className={styles.professionalsGrid}>
        {professionals.map(prof => (
          <ProfessionalCard key={prof.id} professional={prof} />
        ))}
      </div>
      {/* Aqui você pode adicionar botões de Paginação */}
    </main>
  );
}

export default HomePage;