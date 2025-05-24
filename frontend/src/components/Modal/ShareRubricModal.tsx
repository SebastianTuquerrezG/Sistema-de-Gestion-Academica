import { useState} from "react";
import { Dialog, DialogContent, DialogTitle, DialogDescription,DialogFooter } from "@/components/ui/dialog.tsx";
import { Input } from "@/components/ui/input.tsx";
import { Button } from "@/components/ui/button.tsx";
import { Alert, AlertDescription } from "@/components/ui/alert"
import { Switch } from "@/components/ui/switch"
import {Loader2, Share2, AlertCircle, Copy, Mail, Plus, X, LinkIcon, Eye, Edit, UserPlus, Pencil} from "lucide-react"
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { Badge } from "@/components/ui/badge"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { Label } from "@/components/ui/label"
import { ModalProps } from "@/interfaces/ModalProps.ts";

interface props extends ModalProps {
    onShare: (data: {
        email: string;
        permission: string;
        message: string;
        notifyUsers: boolean;
        createLink: boolean;
        linkPermission: string;
    }) => void;
    rubricName: string;
    isLoading?: boolean;
    validationErrors?: string[];
}

interface Colaborador {
    id: string
    name: string
    email: string
    avatar?: string
    permission: string
}
export default function ShareRubricModal({ open, onClose, onShare,rubricName,isLoading=false,validationErrors=[]}: props) {
    const [activeTab, setActiveTab] = useState("personas")
    const [email, setEmail] = useState("")
    const [permission, setPermission] = useState("Editor")
    const [message, setMessage] = useState("")
    const [notifyUsers, setNotifyUsers] = useState(true)
    const [createLink, setCreateLink] = useState(false)
    const [linkPermission, setLinkPermission] = useState("ver")
    const [emails, setEmails] = useState<string[]>([])
    const [localErrors, setLocalErrors] = useState<string[]>([])
    const [collaborators, setCollaborators] = useState<Colaborador[]>([
        {
            id: "1",
            name: "Prof. Ana Martínez",
            email: "ana.martinez@universidad.edu",
            avatar: "/placeholder.svg?height=40&width=40",
            permission: "propietario",
        },
        {
            id: "2",
            name: "Prof. Carlos Rodríguez",
            email: "carlos.rodriguez@universidad.edu",
            avatar: "/placeholder.svg?height=40&width=40",
            permission: "editar",
        },
    ])
    //Funcion para añadir un nuevo colaborador
    const handleAddEmail = () => {
        if (!email) return

        // Validar formato de email
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
        if (!emailRegex.test(email)) {
            setLocalErrors(["El formato del correo electrónico no es válido"])
            return
        }

        // Verificar si ya existe
        if (emails.includes(email) || collaborators.some((c) => c.email === email)) {
            setLocalErrors(["Este correo electrónico ya ha sido añadido"])
            return
        }
        setEmails([...emails, email])
        setEmail("")
        setLocalErrors([])
    }
    //Funcion para eliminar un email de la lista de colaboradores
    const handleRemoveEmail = (emailToRemove: string) => {
        setEmails(emails.filter((e) => e !== emailToRemove))
    }
    //Funcion para cambiar el permiso de un colaborador
    const handleChangePermission = (userId: string, newPermission: string) => {
        setCollaborators(collaborators.map((c) => (c.id === userId ? { ...c, permission: newPermission } : c)))
    }
    //Funcion para eliminar un colaborador
    const handleRemoveCollaborator = (userId: string) => {
        setCollaborators(collaborators.filter((c) => c.id !== userId))
    }
    //Funcion para compartir la rubrica
    const handleSubmit = async () => {
        try {
            // Compartir con cada email de la lista
            for (const e of emails) {
                await onShare({
                    email: e,
                    permission,
                    message,
                    notifyUsers,
                    createLink,
                    linkPermission,
                });
            }
            // Compartir con el email actual si no está en la lista y no está vacío
            if (email && !emails.includes(email)) {
                await onShare({
                    email,
                    permission,
                    message,
                    notifyUsers,
                    createLink,
                    linkPermission,
                });
            }
            // Resetear formulario
            setEmails([]);
            setEmail("");
            setPermission("ver");
            setMessage("");
            setLocalErrors([]);
        } catch {
            setLocalErrors(["Error al compartir la rúbrica"]);
        }
    }
    const getPermissionLabel = (permission: string) => {
        switch (permission) {
            case "Propietario":
                return "Propietario"
            case "Editor":
                return "Editor"
            case "Lector":
                return "Lector"
            default:
                return permission
        }
    }
    const getPermissionBadge = (permission: string) => {
        switch (permission) {
            case "Propietario":
                return <Badge className="bg-gray-500">Propietario</Badge>
            case "Editor":
                return <Badge className="bg-gray-500">Editor</Badge>
            case "Lector":
                return <Badge className="bg-gray-500">Lector</Badge>
            default:
                return <Badge>{permission}</Badge>
        }
    }

    const allErrors = [...localErrors, ...validationErrors]
    const shareLink =`https://rubricas.universidad.edu/share/r/${btoa(rubricName)}`;
    return (
        <Dialog open={open} onOpenChange={onClose}>
            <DialogContent>
                <DialogTitle className="flex items-center gap-2">
                    <Share2 className="h-5 w-5" />
                    Compartir Rubrica
                </DialogTitle>
                <DialogDescription> Comparte "{rubricName}" con otros profesores o colaboradores </DialogDescription>
                <div className="space-y-4 py-4">
                    {allErrors.length > 0 && (
                        <Alert variant="destructive">
                            <AlertCircle className="h-4 w-4" />
                            <AlertDescription>
                                <ul className="list-disc list-inside space-y-1">
                                    {allErrors.map((error, index) => (
                                        <li key={index}>{error}</li>
                                    ))}
                                </ul>
                            </AlertDescription>
                        </Alert>
                    )}

                    <Tabs defaultValue="personas" value={activeTab} onValueChange={setActiveTab}>
                        <TabsList className="grid w-full grid-cols-2">
                            <TabsTrigger value="personas">
                                <UserPlus className="h-4 w-4 mr-2" />
                                Personas
                            </TabsTrigger>
                            <TabsTrigger value="enlace">
                                <LinkIcon className="h-4 w-4 mr-2" />
                                Enlace
                            </TabsTrigger>
                        </TabsList>

                        <TabsContent value="personas" className="space-y-4">
                            <div className="space-y-2">
                                <Label>Añadir personas</Label>
                                <div className="flex gap-2">
                                    <div className="relative flex-1">
                                        <Mail className="absolute left-3 top-3 h-4 w-4 text-gray-400" />
                                        <Input
                                            value={email}
                                            onChange={(e) => setEmail(e.target.value)}
                                            placeholder="Correo electrónico"
                                            className="pl-10"
                                            //Si el usuario presiona Enter, añadir el email
                                            onKeyDown={(e) => {
                                                if (e.key === "Enter") {
                                                    e.preventDefault()
                                                    handleAddEmail()
                                                }
                                            }}
                                        />
                                    </div>
                                    <Select value={permission} onValueChange={setPermission}>
                                        <SelectTrigger className="w-[140px]">
                                            <SelectValue />
                                        </SelectTrigger>
                                        <SelectContent>
                                            <SelectItem value="Lector">
                                                <div className="flex items-center">
                                                    <Eye className="mr-2 h-4 w-4" />
                                                    Lector
                                                </div>
                                            </SelectItem>
                                            <SelectItem value="Comentador">
                                                <div className="flex items-center">
                                                    <Pencil className="mr-2 h-4 w-4" />
                                                    Comentador
                                                </div>
                                            </SelectItem>
                                            <SelectItem value="Editor">
                                                <div className="flex items-center">
                                                    <Edit className="mr-2 h-4 w-4" />
                                                    Editor
                                                </div>
                                            </SelectItem>
                                        </SelectContent>
                                    </Select>
                                    <Button onClick={handleAddEmail} size="icon">
                                        <Plus className="h-4 w-4" />
                                    </Button>
                                </div>
                            </div>

                            {emails.length > 0 && (
                                <div className="space-y-2">
                                    <div className="space-y-2">
                                        {emails.map((email) => (
                                            <div key={email} className="flex items-center justify-between p-2 bg-gray-50 rounded-md">
                                                <div className="flex items-center gap-2">
                                                    <Avatar className="h-8 w-8">
                                                        <AvatarFallback>{email[0].toUpperCase()}</AvatarFallback>
                                                    </Avatar>
                                                    <span>{email}</span>
                                                </div>
                                                <div className="flex items-center gap-2">
                                                    <Badge className="bg-gray-500">{getPermissionLabel(permission)}</Badge>
                                                    <Button variant="ghost" size="icon" onClick={() => handleRemoveEmail(email)}>
                                                        <X className="h-4 w-4" />
                                                    </Button>
                                                </div>
                                            </div>
                                        ))}
                                    </div>
                                </div>
                            )}
                            <div className="space-y-2">
                                <Label>Colaboradores</Label>
                                <div className="space-y-2 max-h-[200px] overflow-y-auto">
                                    {collaborators.map((collaborator) => (
                                        <div key={collaborator.id} className="flex items-center justify-between p-2 bg-gray-50 rounded-md">
                                            <div className="flex items-center gap-2">
                                                <Avatar className="h-8 w-8">
                                                    <AvatarImage src={collaborator.avatar || "/placeholder.svg"} alt={collaborator.name} />
                                                    <AvatarFallback>
                                                        {collaborator.name
                                                            .split(" ")
                                                            .map((n) => n[0])
                                                            .join("")}
                                                    </AvatarFallback>
                                                </Avatar>
                                                <div className="flex flex-col">
                                                    <span className="text-sm font-medium">{collaborator.name}</span>
                                                    <span className="text-xs text-gray-500">{collaborator.email}</span>
                                                </div>
                                            </div>
                                            <div className="flex items-center gap-2">
                                                {collaborator.permission === "Propietario" ? (
                                                    getPermissionBadge(collaborator.permission)
                                                ) : (
                                                    <Select
                                                        value={collaborator.permission}
                                                        onValueChange={(value) => handleChangePermission(collaborator.id, value)}
                                                    >
                                                        <SelectTrigger className="h-8 w-[120px]">
                                                            <SelectValue />
                                                        </SelectTrigger>
                                                        <SelectContent>
                                                            <SelectItem value="editar">Editor</SelectItem>
                                                            <SelectItem value="comentar">Comentador</SelectItem>
                                                            <SelectItem value="ver">Lector</SelectItem>
                                                        </SelectContent>
                                                    </Select>
                                                )}
                                                {collaborator.permission !== "propietario" && (
                                                    <Button variant="ghost" size="icon" onClick={() => handleRemoveCollaborator(collaborator.id)}>
                                                        <X className="h-4 w-4" />
                                                    </Button>
                                                )}
                                            </div>
                                        </div>
                                    ))}
                                </div>
                            </div>

                            <div className="space-y-2">
                                <Label htmlFor="message">Mensaje (opcional)</Label>
                                <Input
                                    id="message"
                                    value={message}
                                    onChange={(e) => setMessage(e.target.value)}
                                    placeholder="Añade un mensaje para los destinatarios"
                                />
                            </div>

                            <div className="flex items-center space-x-2">
                                <Switch id="notify-users" checked={notifyUsers} onCheckedChange={setNotifyUsers} />
                                <Label htmlFor="notify-users">Notificar a los usuarios</Label>
                            </div>
                        </TabsContent>

                        <TabsContent value="enlace" className="space-y-4">
                            <div className="space-y-2">
                                <div className="flex items-center justify-between">
                                    <Label htmlFor="create-link">Crear enlace compartible</Label>
                                    <Switch id="create-link" checked={createLink} onCheckedChange={setCreateLink} />
                                </div>

                                {createLink && (
                                    <>
                                        <div className="flex items-center space-x-2 mt-4">
                                            <div className="relative flex-1">
                                                <LinkIcon className="absolute left-3 top-3 h-4 w-4 text-gray-400" />
                                                <Input value={shareLink} readOnly className="pl-10 pr-20 bg-gray-50" />
                                                <Button
                                                    className="absolute right-1 top-1 h-8"
                                                    variant="ghost"
                                                    onClick={() => {
                                                        navigator.clipboard.writeText(shareLink)
                                                    }}
                                                >
                                                    <Copy className="mr-2 h-4 w-4" />
                                                    Copiar
                                                </Button>
                                            </div>
                                        </div>

                                        <div className="space-y-2 mt-4">
                                            <Label>Permisos del enlace</Label>
                                            <Select value={linkPermission} onValueChange={setLinkPermission}>
                                                <SelectTrigger>
                                                    <SelectValue />
                                                </SelectTrigger>
                                                <SelectContent>
                                                    <SelectItem value="ver">
                                                        <div className="flex items-center">
                                                            <Eye className="mr-2 h-4 w-4" />
                                                            Cualquiera con el enlace puede ver
                                                        </div>
                                                    </SelectItem>
                                                    <SelectItem value="comentar">
                                                        <div className="flex items-center">
                                                            <Edit className="mr-2 h-4 w-4" />
                                                            Cualquiera con el enlace puede comentar
                                                        </div>
                                                    </SelectItem>
                                                    <SelectItem value="editar">
                                                        <div className="flex items-center">
                                                            <Edit className="mr-2 h-4 w-4" />
                                                            Cualquiera con el enlace puede editar
                                                        </div>
                                                    </SelectItem>
                                                </SelectContent>
                                            </Select>
                                        </div>

                                        <Alert className="mt-4">
                                            <AlertCircle className="h-4 w-4" />
                                            <AlertDescription>
                                                Cualquier persona con este enlace podrá acceder a la rúbrica con los permisos seleccionados.
                                            </AlertDescription>
                                        </Alert>
                                    </>
                                )}
                            </div>
                        </TabsContent>
                    </Tabs>
                </div>
                <DialogFooter>
                    <Button variant="outline" onClick={onClose} disabled={isLoading}>
                        Cancelar
                    </Button>
                    <Button
                        onClick={handleSubmit}
                        disabled={isLoading || (activeTab === "personas" && emails.length === 0 && collaborators.length === 0)}
                    >
                        {isLoading && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
                        Compartir
                    </Button>
                </DialogFooter>
            </DialogContent>
        </Dialog>
    )
}
