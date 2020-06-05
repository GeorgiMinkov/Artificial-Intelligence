using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Xml.Serialization;

namespace PhotoStudio.Models
{
    public class PhotoCameras
    {
        [XmlElement("camera")]
        public List<Camera> camerasList = new List<Camera>();
    }
}