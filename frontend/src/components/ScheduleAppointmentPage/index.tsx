import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import {
  getProfessionalById,
  getAvailableSlotsForProfessional,
  createAppointment,
  getErrorMessage,
  type ProfessionalPublicDTO,
  type ServiceOfferedDTO,
  type AvailableSlotDTO,
  type CreateAppointmentDTO
} from '../../services/api';
import styles from './styles.module.css';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import { isAfter, startOfDay, endOfDay } from 'date-fns';

function ScheduleAppointmentPage() {
  const { professionalId, serviceId } = useParams<{ professionalId: string; serviceId?: string }>();
  const navigate = useNavigate();

  const [professional, setProfessional] = useState<ProfessionalPublicDTO | null>(null);
  const [selectedService, setSelectedService] = useState<ServiceOfferedDTO | null>(null);
  const [selectedDate, setSelectedDate] = useState<Date>(new Date());
  const [availableSlots, setAvailableSlots] = useState<AvailableSlotDTO[]>([]);
  const [selectedSlot, setSelectedSlot] = useState<AvailableSlotDTO | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [isScheduling, setIsScheduling] = useState(false);
  const [successMessage, setSuccessMessage] = useState<string | null>(null);

  const parsedProfessionalId = professionalId ? BigInt(professionalId) : undefined;
  const parsedServiceId = serviceId ? BigInt(serviceId) : undefined;

  useEffect(() => {
    if (!parsedProfessionalId) {
      setError("ID do profissional não fornecido.");
      setIsLoading(false);
      return;
    }

    async function fetchProfessionalDetails() {
      setIsLoading(true);
      setError(null);
      try {
        const professionalResponse = await getProfessionalById(parsedProfessionalId!);
        const fetchedProfessional = professionalResponse.data;
        setProfessional(fetchedProfessional);

        if (parsedServiceId && fetchedProfessional.services) {
          const service = fetchedProfessional.services.find(s => s.id === parsedServiceId);
          if (service) {
            setSelectedService(service);
          } else {
            setError("Serviço selecionado não encontrado para este profissional.");
          }
        }
      } catch (err) {
        console.error("Erro ao carregar detalhes do profissional/serviço:", err);
        setError(getErrorMessage(err));
        setProfessional(null);
      } finally {
        setIsLoading(false);
      }
    }
    fetchProfessionalDetails();
  }, [parsedProfessionalId, parsedServiceId]);

  useEffect(() => {
    if (!parsedProfessionalId || !selectedDate) return;

    async function fetchAvailableSlots() {
      setError(null);
      try {
        const startOfSelectedDay = startOfDay(selectedDate);
        const endOfSelectedDay = endOfDay(selectedDate);

        const response = await getAvailableSlotsForProfessional(
          parsedProfessionalId!,
          startOfSelectedDay.toISOString(),
          endOfSelectedDay.toISOString()
        );

        const now = new Date();
        const futureSlots = response.data.filter(slot =>
          isAfter(new Date(slot.startTime), now)
        );

        setAvailableSlots(futureSlots);
        setSelectedSlot(null);
      } catch (err) {
        console.error("Erro ao carregar horários disponíveis:", err);
        setError(getErrorMessage(err));
        setAvailableSlots([]);
      }
    }
    fetchAvailableSlots();
  }, [parsedProfessionalId, selectedDate]);

  const handleScheduleSubmit = async () => {
    if (!parsedProfessionalId || !selectedService || !selectedSlot) {
      setError("Por favor, selecione um profissional, serviço e horário.");
      return;
    }

    setIsScheduling(true);
    setError(null);
    setSuccessMessage(null);

    try {
      const appointmentData: CreateAppointmentDTO = {
        professionalId: parsedProfessionalId,
        serviceId: selectedService.id,
        appointmentTime: selectedSlot.startTime,
      };

      await createAppointment(appointmentData);
      setSuccessMessage(`Agendamento confirmado para ${professional?.name} - ${selectedService.name} às ${new Date(selectedSlot.startTime).toLocaleTimeString('pt-BR', { hour: '2-digit', minute: '2-digit' })}!`);
      setSelectedDate(new Date());
      setSelectedSlot(null);
      setAvailableSlots([]);
    } catch (err) {
      console.error("Erro ao agendar serviço:", err);
      setError(getErrorMessage(err));
    } finally {
      setIsScheduling(false);
    }
  };

  if (isLoading) {
    return <div className={styles.statusMessage}>Carregando opções de agendamento...</div>;
  }

  if (error) {
    return <div className={`${styles.statusMessage} ${styles.error}`}>Erro: {error}</div>;
  }

  if (!professional) {
    return <div className={styles.statusMessage}>Profissional não encontrado para agendamento.</div>;
  }

  return (
    <main className={styles.scheduleContainer}>
      <h1 className={styles.title}>Agendar Serviço com {professional.name}</h1>

      {successMessage && <div className={styles.successMessage}>{successMessage}</div>}

      <section className={styles.serviceSelection}>
        <h2>Serviço Selecionado:</h2>
        {selectedService ? (
          <div className={styles.selectedServiceCard}>
            <h3>{selectedService.name}</h3>
            <p>{selectedService.description}</p>
            <p>Preço: R$ {selectedService.price.toFixed(2).replace('.', ',')}</p>
          </div>
        ) : (
          <p>Por favor, selecione um serviço para agendar.</p>
        )}
        {professional.services && professional.services.length > 0 && (
          <div className={styles.otherServices}>
            <h3>{selectedService ? "Ou escolha outro serviço:" : "Escolha um serviço:"}</h3>
            <div className={styles.servicesGrid}>
              {professional.services.map(s => (
                <button
                  key={String(s.id)}
                  className={`${styles.serviceOptionButton} ${selectedService?.id === s.id ? styles.selected : ''}`}
                  onClick={() => setSelectedService(s)}
                  disabled={isScheduling}
                >
                  {s.name} - R$ {s.price.toFixed(2).replace('.', ',')}
                </button>
              ))}
            </div>
          </div>
        )}
      </section>

      <section className={styles.dateSelection}>
        <h2>Escolha a Data:</h2>
        <DatePicker
          selected={selectedDate}
          onChange={(date: Date | null) => {
            if (date) setSelectedDate(date);
          }}
          dateFormat="dd/MM/yyyy"
          minDate={new Date()}
          className={styles.datePickerInput}
          disabled={isScheduling}
        />
      </section>

      <section className={styles.slotsSelection}>
        <h2>Horários Disponíveis ({selectedDate.toLocaleDateString('pt-BR')}):</h2>
        {availableSlots.length > 0 ? (
          <div className={styles.slotsGrid}>
            {availableSlots.map(slot => (
              <button
                key={String(slot.id)}
                className={`${styles.slotButton} ${selectedSlot?.id === slot.id ? styles.selected : ''}`}
                onClick={() => setSelectedSlot(slot)}
                disabled={isScheduling}
              >
                {new Date(slot.startTime).toLocaleTimeString('pt-BR', { hour: '2-digit', minute: '2-digit' })}
              </button>
            ))}
          </div>
        ) : (
          <p>Nenhum horário disponível para a data selecionada ou os horários já passaram.</p>
        )}
      </section>

          <div className={styles.actionButtons}>
        <button
          className={styles.confirmButton}
          onClick={handleScheduleSubmit}
          disabled={!selectedService || !selectedSlot || isScheduling}
        >
          {isScheduling ? 'Agendando...' : 'Confirmar Agendamento'}
        </button>
        <button
          className={styles.cancelButton}
          onClick={() => navigate(-1)}
          disabled={isScheduling}
        >
          Cancelar
        </button>
      </div>
    </main>
  );
}

export default ScheduleAppointmentPage;
