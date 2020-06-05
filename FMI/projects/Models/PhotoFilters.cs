using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Xml.Serialization;

namespace PhotoStudio.Models
{
    public class PhotoFilters
    {
        [XmlElement("filter")]
        public List<Filter> filtersList = new List<Filter>();
    }
}