import React from 'react';
import { CircularProgress, Alert, Box, Typography } from '@mui/material';
import ErrorOutlineIcon from '@mui/icons-material/ErrorOutline';
import InfoOutlinedIcon from '@mui/icons-material/InfoOutlined';

interface EstadisticasStateHandlerProps {
  loading: boolean;
  error: string | null;
  hasData: boolean;
  filtrosCompletos?: boolean;
}

const EstadisticasStateHandler: React.FC<EstadisticasStateHandlerProps> = ({
  loading,
  error,
  hasData,
  filtrosCompletos = true
}) => {
  if (loading) {
    return (
      <Box
        sx={{
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
          justifyContent: 'center',
          minHeight: '300px',
          gap: 2
        }}
      >
        <CircularProgress size={60} />
        <Typography variant="h6" color="text.secondary">
          Cargando estadísticas...
        </Typography>
      </Box>
    );
  }

  if (error) {
    return (
      <Box
        sx={{
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
          justifyContent: 'center',
          minHeight: '300px',
          gap: 2,
          p: 3
        }}
      >
        <ErrorOutlineIcon sx={{ fontSize: 60, color: 'error.main' }} />
        <Alert severity="error" sx={{ width: '100%', maxWidth: '600px' }}>
          {error}
        </Alert>
        <Typography variant="body1" color="text.secondary" align="center">
          Intente seleccionar diferentes filtros para ver los resultados
        </Typography>
      </Box>
    );
  }

  if (!hasData) {
    if (!filtrosCompletos) {
      return (
        <Box
          sx={{
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            justifyContent: 'center',
            minHeight: '300px',
            gap: 2,
            p: 3
          }}
        >
          <InfoOutlinedIcon sx={{ fontSize: 60, color: 'info.main' }} />
          <Typography variant="h6" color="text.secondary" align="center">
            Complete todos los filtros para ver las estadísticas
          </Typography>
          <Typography variant="body1" color="text.secondary" align="center">
            Seleccione materia, período y rúbrica para mostrar los resultados
          </Typography>
        </Box>
      );
    }
    return (
      <Box
        sx={{
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
          justifyContent: 'center',
          minHeight: '300px',
          gap: 2,
          p: 3
        }}
      >
        <InfoOutlinedIcon sx={{ fontSize: 60, color: 'info.main' }} />
        <Typography variant="h6" color="text.secondary" align="center">
          No hay estadísticas relacionadas a estos filtros.
        </Typography>
        <Typography variant="body1" color="text.secondary" align="center">
          Intenta con otra materia, período o rúbrica para ver resultados.
        </Typography>
      </Box>
    );
  }

  return null;
};

export default EstadisticasStateHandler; 