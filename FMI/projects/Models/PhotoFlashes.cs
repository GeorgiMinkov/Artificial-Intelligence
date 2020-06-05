using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Xml.Serialization;

namespace PhotoStudio.Models
{
    public class PhotoFlashes
    {
        [XmlElement("flash")]
        public List<Flash> flashesList = new List<Flash>();
    }
}