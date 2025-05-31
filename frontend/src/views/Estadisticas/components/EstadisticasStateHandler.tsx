import React from 'react';
import { CircularProgress, Alert, Box, Typography } from '@mui/material';
import ErrorOutlineIcon from '@mui/icons-material/ErrorOutline';
import InfoOutlinedIcon from '@mui/icons-material/InfoOutlined';

interface EstadisticasStateHandlerProps {
  loading: boolean;
  error: string | null;
  hasData: boolean;
}

const EstadisticasStateHandler: React.FC<EstadisticasStateHandlerProps> = ({
  loading,
  error,
  hasData
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
          No hay datos para esta combinación de filtros
        </Typography>
        <Typography variant="body1" color="text.secondary" align="center">
          Intente seleccionar diferentes filtros para ver los resultados
        </Typography>
      </Box>
    );
  }

  return null;
};

export default EstadisticasStateHandler; 