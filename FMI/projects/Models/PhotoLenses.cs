using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Xml.Serialization;

namespace PhotoStudio.Models
{
    public class PhotoLenses
    {
        [XmlElement("lens")]
        public List<Lens> lensesList = new List<Lens>();
    }
}